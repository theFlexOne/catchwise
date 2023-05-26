import { createContext, useContext, useEffect, useState } from "react";
import axios, { CanceledError } from "axios";

type LatLngLiteral = google.maps.LatLngLiteral;

interface LakesProviderProps {
  children: React.ReactNode;
}

interface Range {
  minLat: number;
  maxLat: number;
  minLng: number;
  maxLng: number;
}

const serverUrl = import.meta.env.VITE_SERVER_URL;

const LakesContext = createContext<null | any[]>(null);

const LakesProvider: React.FC<LakesProviderProps> = ({ children }) => {
  const [lakes, setLakes] = useState<any[] | null>(null);

  async function getLakesInRange({ minLat, maxLat, minLng, maxLng }: Range) {
    const response = await axios.get(
      `${serverUrl}/api/v1/lakes/in-range?minLat=${minLat}&maxLat=${maxLat}&minLng=${minLng}&maxLng=${maxLng}`
    );
    return response.data;
  }

  useEffect(() => {
    async function getAllLakes(controller: AbortController) {
      try {
        const response = await axios.get(`${serverUrl}/api/v1/lakes`, {
          signal: controller.signal,
        });
        return response.data;
      } catch (error) {
        if (axios.isCancel(error)) {
          console.log(error.message);
        } else {
          throw error;
        }
      }
    }

    const controller = new AbortController();
    getAllLakes(controller).then(setLakes).catch(console.error);
    return () => controller.abort();
  }, []);

  return (
    <LakesContext.Provider value={lakes}>{children}</LakesContext.Provider>
  );
};

const useLakes = () => {
  const context = useContext(LakesContext);
  if (context === null) {
    throw new Error("useLakes must be used within a LakesProvider");
  }
  return context;
};

export { LakesProvider, useLakes };
