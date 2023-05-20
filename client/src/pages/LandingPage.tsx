import Map from "../components/Map";
import { useLoadScript } from "@react-google-maps/api";

interface LandingPageProps {
  lat: number;
  lon: number;
}

const LandingPage: React.FC<LandingPageProps> = ({ lat, lon }) => {
  const { isLoaded, loadError } = useLoadScript({
    googleMapsApiKey: import.meta.env.VITE_GOOGLE_MAPS_API_KEY,
  });

  console.log("isLoaded", isLoaded);
  console.log("loadError", loadError);

  if (loadError) return <div>Error loading maps</div>;
  if (!isLoaded) return <div>Loading Maps</div>;

  return (
    <div className="flex-grow">
      <Map lat={lat} lon={lon} className="w-full h-full" />
    </div>
  );
};

export default LandingPage;
