import { useEffect, useRef } from "react";
import { GoogleMap } from "@react-google-maps/api";
import { twMerge } from "tailwind-merge";

interface MapProps {
  lat: number;
  lon: number;
  className: string;
}

const Map: React.FC<MapProps> = ({ lat, lon: lng, className = "" }) => {
  console.log(lat, lng);

  className = twMerge(className, "w-full h-full");

  return (
    <GoogleMap
      id="map"
      mapContainerClassName={className}
      center={{ lat: 44, lng: -81 }}
      zoom={8}
    />
  );
};

export default Map;
