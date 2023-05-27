import { Loader } from "@googlemaps/js-api-loader";
import { useEffect, useRef, useState } from "react";

const ST_PAUL = { lat: 44.9537, lng: -93.09 };
const BRAINERD = { lat: 46.3588, lng: -94.2008 };

const Map = () => {
  const mapRef = useRef<HTMLDivElement>(null);
  const [map, setMap] = useState<google.maps.Map | null>(null);

  useEffect(() => {
    const loader = new Loader({
      apiKey: import.meta.env.VITE_GOOGLE_API_KEY as string,
      version: "weekly",
    });

    loader.load().then(async () => {
      const { Map } = (await google.maps.importLibrary(
        "maps"
      )) as google.maps.MapsLibrary;
      const map = new Map(mapRef.current as HTMLDivElement, {
        center: ST_PAUL,
        zoom: 10,
        mapId: "45aff3ba656e7008",
      });
      setMap(map);
    });
  }, []);

  map && console.log(map);
  map && console.log(map.getBounds());

  return <div ref={mapRef} className="absolute inset-0" />;
};

export default Map;
