// import ColorSchemes from "./components/ColorSchemes";
import { useEffect, useState } from "react";
import RootLayout from "./layouts/RootLayout";
import LandingPage from "./pages/LandingPage";

interface Link {
  url: string;
  label: string;
}

const links: Link[] = [
  { url: "#", label: "Home" },
  { url: "#", label: "About" },
  { url: "#", label: "Contact" },
];

function App() {
  const [position, setPosition] = useState<GeolocationPosition>();

  const [coordinatesError, setCoordinatesError] =
    useState<GeolocationPositionError | null>(null);
  const [googleMapsError, setGoogleMapsError] = useState<Error | null>(null);

  useEffect(() => {
    navigator.geolocation.getCurrentPosition(
      (position) => {
        setPosition(position);
      },
      (error) => {
        setCoordinatesError(error);
      }
    );
  }, []);

  position && console.log(position.coords);

  return (
    <RootLayout links={links}>
      {position?.coords && (
        <LandingPage
          lat={position.coords.latitude}
          lon={position.coords.longitude}
        />
      )}
    </RootLayout>
  );
}

export default App;
