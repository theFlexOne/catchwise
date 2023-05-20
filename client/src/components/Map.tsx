import { twMerge } from "tailwind-merge";

interface MapProps {
  lat: number;
  lon: number;
  className: string;
}

const Map: React.FC<MapProps> = ({ lat, lon: lng, className = "" }) => {
  className = twMerge(className, "w-full h-full");

  return <div id="map" className={className}></div>;
};

export default Map;
