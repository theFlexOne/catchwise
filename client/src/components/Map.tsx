import {
  GoogleMap,
  InfoWindow,
  Marker,
  useJsApiLoader,
} from "@react-google-maps/api";
import { useCallback, useState } from "react";
import { useLakes } from "../context/LakesContext";
import pinSvg from "../assets/pin.svg";

const API_KEY = import.meta.env.VITE_GOOGLE_MAPS_API_KEY as string;

type LatLngLiteral = google.maps.LatLngLiteral;

interface MapProps {
  center: LatLngLiteral;
  zoom?: number;
}

const Map: React.FC<MapProps> = ({ center, zoom = 8 }) => {
  const { isLoaded, loadError } = useJsApiLoader({
    googleMapsApiKey: API_KEY,
    mapIds: ["45aff3ba656e7008"],
  });

  const lakes = useLakes();
  const [mapObject, setMapObject] = useState<google.maps.Map | null>(null);
  const [zoomLevel, setZoomLevel] = useState<number | undefined>(zoom);

  const onLoad = useCallback((map: google.maps.Map) => {
    const interval = setInterval(() => {
      const bounds = map.getBounds();
      if (bounds) {
        setMapObject(map);
        clearInterval(interval);
      }
    }, 10);
  }, []);

  const handleZoomChange = useCallback(() => {
    if (mapObject) {
      setZoomLevel(mapObject.getZoom());
    }
  }, [mapObject]);

  const buildMapMarker = (acc: any[], lake: any, i: number, lakes: any[]) => {
    i === 0 && console.log(lake);
    const mapBoundary = mapObject?.getBounds();
    if (mapBoundary && lake.coodinates) {
      if (!mapBoundary.contains(lake.coodinates)) {
        return acc;
      }
    }
    console.log(lake);

    return (
      <Marker
        key={i}
        position={lake.coodinates}
        title={lake.name}
        label={lake.name}
        icon={pinSvg}
      ></Marker>
    );
  };

  mapObject && console.log(mapObject.getBounds());
  lakes && console.log(lakes);

  return isLoaded ? (
    <GoogleMap
      mapContainerStyle={{
        position: "absolute",
        inset: 0,
        maxWidth: "960px",
        marginInline: "auto",
      }}
      center={center}
      zoom={zoomLevel}
      onLoad={onLoad}
      onZoomChanged={handleZoomChange}
    >
      {lakes && lakes.reduce(buildMapMarker, [])}
    </GoogleMap>
  ) : (
    <div>Loading...</div>
  );
};

export default Map;
