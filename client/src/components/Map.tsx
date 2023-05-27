import { GoogleMap, useJsApiLoader } from "@react-google-maps/api";
import axios from "axios";
import { useRef, useState } from "react";
import LakeMarker from "./LakeMarker";

const API_KEY = import.meta.env.VITE_GOOGLE_MAPS_API_KEY as string;
const SERVER_URL = import.meta.env.VITE_SERVER_URL as string;

type LatLngLiteral = google.maps.LatLngLiteral;
type MapOptions = google.maps.MapOptions;

const googleApiConfig = {
  googleMapsApiKey: API_KEY,
  mapIds: ["45aff3ba656e7008"],
};

const mapOptions: MapOptions = {
  mapId: "45aff3ba656e7008",
  disableDefaultUI: true,
  minZoom: 11,
  maxZoom: 17,
};

export default function Map({ center, zoom = 14 }: MapProps) {
  const { isLoaded, loadError } = useJsApiLoader(googleApiConfig);

  const [visibleLakes, setVisibleLakes] = useState<any[]>([]);
  const [mapObject, setMapObject] = useState<google.maps.Map | null>(null); // could be a ref if all interactions are done via callbacks/handlers
  const [selectedLake, setSelectedLake] = useState<any | null>(null);

  const debounceRef = useRef<ReturnType<typeof setTimeout> | null>(null);

  const handleLoad = (map: google.maps.Map) => {
    const interval = setInterval(() => {
      const bounds = map.getBounds();
      if (
        bounds?.getNorthEast().lat() === bounds?.getSouthWest().lat() &&
        bounds?.getNorthEast().lng() === bounds?.getSouthWest().lng()
      )
        return;

      clearInterval(interval);
      setMapObject(map);
      fetchLakes(map, setVisibleLakes);
    }, 10);
  };

  const handleBoundsChange = () => {
    if (!mapObject) return;
    if (debounceRef.current) clearTimeout(debounceRef.current);
    debounceRef.current = setTimeout(() => {
      fetchLakes(mapObject, setVisibleLakes);
    }, 150);
  };

  const handleMarkerClick = (lake: any) => {
    setSelectedLake(lake);
  };

  if (loadError) return <div>Error loading map. Try refreshing the page?</div>;

  selectedLake && console.log(selectedLake);

  return isLoaded ? (
    <GoogleMap
      mapContainerStyle={{
        position: "absolute",
        inset: 0,
        maxWidth: "960px",
        marginInline: "auto",
      }}
      options={mapOptions}
      center={center}
      zoom={zoom}
      onLoad={handleLoad}
      onBoundsChanged={handleBoundsChange}
    >
      {visibleLakes.map((lake) => (
        <LakeMarker
          key={lake.id}
          lake={lake}
          onClick={handleMarkerClick}
          isSelected={selectedLake?.id === lake.id}
        />
      ))}
    </GoogleMap>
  ) : (
    <div>Loading...</div>
  );
}

function fetchLakes(
  mapObject: google.maps.Map,
  callback: React.SetStateAction<any>
) {
  const max = mapObject.getBounds()?.getNorthEast().toJSON();
  const min = mapObject.getBounds()?.getSouthWest().toJSON();

  const searchParams = new URLSearchParams({
    minLat: (min?.lat ?? 0).toString(),
    minLng: (min?.lng ?? 0).toString(),
    maxLat: (max?.lat ?? 0).toString(),
    maxLng: (max?.lng ?? 0).toString(),
  });

  const url = new URL(`${SERVER_URL}/api/v1/lakes/in-range`);
  url.search = searchParams.toString();

  axios
    .get(url.toString())
    .then((res) => callback(res.data))
    .catch(console.error);
}

interface MapProps {
  center: LatLngLiteral;
  zoom?: number;
}
