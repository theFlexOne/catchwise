import GoogleMap from "google-map-react";
import { twMerge } from "tailwind-merge";
import { useLakes } from "../context/LakesContext";
import { useEffect } from "react";

const DEFAULT_CENTER = { lat: 44.9537, lng: -93.09 };

interface MapProps {
  center?: {
    lat: number;
    lng: number;
  };
  zoom?: number;
  className?: string;
}

const Map: React.FC<MapProps> = ({
  center = DEFAULT_CENTER,
  zoom = 10,
  className = "",
}) => {
  const getLakesInRange = useLakes();

  className = twMerge("absolute inset-0 max-w-5xl mx-auto", className);

  const handleLoad = async (map: any) => {
    console.log(map);

    // const { ne, sw } = bounds;
    // const { lat: minLat, lng: minLng } = sw;
    // const { lat: maxLat, lng: maxLng } = ne;
    // const lakes = await getLakesInRange({
    //   minLat,
    //   maxLat,
    //   minLng,
    //   maxLng,
    // });
    // console.log(lakes);
    // return undefined;
  };

  return (
    <div className={className}>
      <GoogleMap
        bootstrapURLKeys={{ key: import.meta.env.VITE_GOOGLE_MAPS_API_KEY }}
        options={{ mapId: "45aff3ba656e7008" }}
        defaultCenter={center}
        defaultZoom={zoom}
        onChange={console.log}
        onTilesLoaded={() => null}
      ></GoogleMap>
    </div>
  );
};

export default Map;
