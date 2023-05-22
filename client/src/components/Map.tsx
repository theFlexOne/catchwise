import GoogleMap, { Coords } from "google-map-react";

const DEFAULT_CENTER = { lat: 44.9537, lng: -93.09 };

interface MapProps {
  center?: {
    lat: number;
    lng: number;
  };
  zoom?: number;
}

const handleLoaded = (map: any) => {
  console.log(map);
};

const Map: React.FC<MapProps> = ({ center = DEFAULT_CENTER, zoom = 10 }) => {
  return (
    <div className="absolute inset-0">
      <GoogleMap
        bootstrapURLKeys={{ key: import.meta.env.VITE_GOOGLE_MAPS_API_KEY }}
        options={{ mapId: "45aff3ba656e7008" }}
        defaultCenter={center}
        defaultZoom={zoom}
        onGoogleApiLoaded={handleLoaded}
      ></GoogleMap>
    </div>
  );
};

export default Map;
