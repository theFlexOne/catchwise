import { useEffect, useState } from "react";
import ColorSchemes from "./components/ColorSchemes";
import RootLayout from "./layouts/RootLayout";
import LandingPage from "./pages/LandingPage";
import { LakesProvider } from "./context/LakesContext";

type LatLonLiteral = google.maps.LatLngLiteral;

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
  const [coords, setCoords] = useState<LatLonLiteral | null>(null);
  const [coordinatesError, setCoordinatesError] =
    useState<GeolocationPositionError | null>(null);

  useEffect(() => {
    navigator.geolocation.getCurrentPosition(
      (position) => {
        const { latitude, longitude } = position.coords;
        setCoords({ lat: latitude, lng: longitude });
      },
      () => {
        const url = `https://www.googleapis.com/geolocation/v1/geolocate?key=${
          import.meta.env.GOOGLE_MAPS_API_KEY
        }`;
        fetch(url, {
          method: "POST",
          body: JSON.stringify({ considerIp: true }),
        })
          .then((res) => res.json())
          .then(console.log);
      }
    );
  }, []);

  return (
    <LakesProvider>
      <RootLayout links={links}>
        {coords ? (
          <LandingPage coords={coords} />
        ) : (
          <div className="flex justify-center items-center h-screen">
            <p className="text-4xl text-white">
              {coordinatesError?.message ?? "Loading..."}
            </p>
          </div>
        )}
      </RootLayout>
    </LakesProvider>
  );
}

export default App;
