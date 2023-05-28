import { GoogleMap, useJsApiLoader } from "@react-google-maps/api";
import axios from "axios";
import { useRef, useState } from "react";
import LakeMarker from "./LakeMarker";
import buildUrl from "../helpers/buildUrl";
import { Lake } from "../types/Lake";
import MapSearchBar from "./MapSearchBar";
import useGooglePlaces from "../hooks/useGoogleApi";
import { FieldValues } from "../enums/FieldValues";

const SERVER_URL = import.meta.env.VITE_SERVER_URL as string;
const GOOGLE_API_CONFIG = {
  googleMapsApiKey: import.meta.env.VITE_GOOGLE_API_KEY as string,
  mapIds: ["45aff3ba656e7008"],
};
const MAP_OPTIONS: MapOptions = {
  mapId: "45aff3ba656e7008",
  disableDefaultUI: true,
  minZoom: 11,
  maxZoom: 17,
};

export default function Map({ center, zoom = 14 }: MapProps) {
  const { isLoaded, loadError } = useJsApiLoader(GOOGLE_API_CONFIG);
  const [mapObject, setMapObject] = useState<google.maps.Map | null>(null); //? could be a ref if all interactions are done via callbacks/handlers

  const [visibleLakes, setVisibleLakes] = useState<Lake[]>([]);
  const [selectedLake, setSelectedLake] = useState<Lake | null>(null);
  const [selectedPlaceDetails, setSelectedPlaceDetails] = useState<any | null>(
    null
  );

  const [currentCenter, setCurrentCenter] = useState<LatLngLiteral>(center);
  const [currentBounds, setCurrentBounds] = useState<LatLngBounds | null>(null);

  const { searchForPlace, getPlaceDetails } = useGooglePlaces();

  const debounceRef = useRef<ReturnType<typeof setTimeout> | null>(null);

  function handleLoad(map: google.maps.Map) {
    //? using and clearing an interval because onLoad()
    //? is called before the map is actually ready
    const interval = setInterval(() => {
      const bounds = map.getBounds();

      //? until map has fully rendered, both NE and SW
      //? will be equal to each other (center)
      if (
        bounds?.getNorthEast().lat() === bounds?.getSouthWest().lat() &&
        bounds?.getNorthEast().lng() === bounds?.getSouthWest().lng()
      )
        return;

      clearInterval(interval);
      setMapObject(map);
      fetchLakesWithinBounds(bounds, setVisibleLakes);
    }, 10);
  }

  function handleBoundsChange() {
    if (!mapObject) return;
    if (debounceRef.current) clearTimeout(debounceRef.current);
    debounceRef.current = setTimeout(() => {
      const newBounds = mapObject.getBounds();
      if (!newBounds) return;

      const ne = newBounds.getNorthEast();
      const sw = newBounds.getSouthWest();
      if (!currentBounds?.contains(ne) || !currentBounds?.contains(sw))
        fetchLakesWithinBounds(newBounds, setVisibleLakes);

      setCurrentBounds(newBounds);
    }, 150);
  }

  function handleMarkerClick(lake: Lake) {
    return () => setSelectedLake(lake);
  }

  async function handleSubmit(input: string) {
    input = input.trim() + " lake";

    const fields = [FieldValues.name, FieldValues.place_id];

    const placeSearch = await searchForPlace(input, fields);
    const placeId = placeSearch?.candidates[0]?.place_id || null;

    if (placeId) {
      const details = await getPlaceDetails(placeId, fields);
      console.log(details);
      const newCenter = details?.result.geometry.location as LatLngLiteral;
      mapObject?.setCenter(newCenter);
    }
  }

  if (loadError) return <div>Error loading map. Try refreshing the page?</div>;

  return isLoaded ? (
    <div className="absolute inset-0 max-w-[960px] mx-auto">
      <GoogleMap
        mapContainerStyle={{
          position: "absolute",
          inset: 0,
        }}
        options={MAP_OPTIONS}
        center={currentCenter}
        zoom={zoom}
        onLoad={handleLoad}
        onBoundsChanged={handleBoundsChange}
      >
        {visibleLakes.map((lake) => (
          <LakeMarker
            key={lake.id}
            lake={lake}
            onClick={handleMarkerClick(lake)}
            isSelected={selectedLake?.id === lake.id}
          />
        ))}
      </GoogleMap>
      <MapSearchBar
        className="absolute z-10 top-4 right-0"
        onSubmit={handleSubmit}
      />
    </div>
  ) : (
    <div>Loading...</div>
  );
}

async function fetchLakesWithinBounds(
  bounds: google.maps.LatLngBounds | undefined,
  success: React.Dispatch<React.SetStateAction<Lake[]>>,
  failure?: (error: unknown) => void
) {
  const max = bounds?.getNorthEast().toJSON();
  const min = bounds?.getSouthWest().toJSON();

  const params = {
    minLat: (min?.lat ?? 0).toString(),
    minLng: (min?.lng ?? 0).toString(),
    maxLat: (max?.lat ?? 0).toString(),
    maxLng: (max?.lng ?? 0).toString(),
  };

  const url = buildUrl(`${SERVER_URL}/api/v1/lakes/in-range`, params);

  try {
    const { data } = await axios.get(url);
    success(data);
  } catch (error) {
    if (failure) failure(error);
    else console.error(error);
  }
}

type MapProps = {
  center: LatLngLiteral;
  zoom?: number;
};
type LatLngLiteral = google.maps.LatLngLiteral;
type MapOptions = google.maps.MapOptions;
type LatLngBounds = google.maps.LatLngBounds;
type LatLngBoundsLiteral = google.maps.LatLngBoundsLiteral;
