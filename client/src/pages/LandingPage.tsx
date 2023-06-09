import Map from "../components/Map";
// import Map from "../components/MapWithHooks";
// import Map from "../components/MapVanilla";

type LatLngLiteral = google.maps.LatLngLiteral;

interface LandingPageProps {
  coords: LatLngLiteral;
}

const LandingPage: React.FC<LandingPageProps> = ({ coords }) => {
  return <Map center={coords} />;
};

export default LandingPage;
