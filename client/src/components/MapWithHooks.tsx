import {
  GoogleMapsProvider,
  useGoogleMap,
} from "@ubilabs/google-maps-react-hooks";
import { useRef, useState } from "react";

const API_KEY = import.meta.env.VITE_GOOGLE_MAPS_API_KEY;

const DEFAULT_CENTER = { lat: 44.9537, lng: -93.09 };

interface MapProps {
  center?: google.maps.LatLng | google.maps.LatLngLiteral | undefined;
  zoom?: number;
}

const Map: React.FC<MapProps> = ({ center = DEFAULT_CENTER, zoom = 10 }) => {
  const mapDivRef = useRef<HTMLDivElement>(null);

  const mapOptions = {
    center,
    zoom,
  };

  return (
    <GoogleMapsProvider
      googleMapsAPIKey={API_KEY}
      mapContainer={mapDivRef.current}
      mapOptions={mapOptions}
    >
      <div ref={mapDivRef} className="h-[500px]" />
    </GoogleMapsProvider>
  );
};

export default Map;
