import Map from "../components/Map";

type LatLngLiteral = google.maps.LatLngLiteral;

interface LandingPageProps {
  coords: LatLngLiteral;
}

const LandingPage: React.FC<LandingPageProps> = ({ coords }) => {
  return <Map />;
};

export default LandingPage;
