import Map from "../components/Map";

interface LandingPageProps {
  lat: number;
  lon: number;
}

const LandingPage: React.FC<LandingPageProps> = ({ lat, lon }) => {
  return (
    <div className="flex-grow">
      <Map lat={lat} lon={lon} className="w-full h-full" />
    </div>
  );
};

export default LandingPage;
