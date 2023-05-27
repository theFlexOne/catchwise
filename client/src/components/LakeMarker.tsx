import { Marker, InfoWindow } from "@react-google-maps/api";
import axios from "axios";
import { useState } from "react";

const SERVER_URL = import.meta.env.VITE_SERVER_URL as string;

interface LakeMarkerProps {
  lake: any;
  children?: React.ReactNode;
  onClick?: (lake: any) => void;
  isSelected?: boolean;
}

const LakeMarker = ({ lake, onClick, isSelected }: LakeMarkerProps) => {
  const [fishList, setFishList] = useState<any[] | null>(null);

  const handleMarkerClick = async () => {
    console.log(lake);

    if (onClick) onClick(lake);

    if (fishList == null) await fetchFishList(lake, setFishList);
  };

  return (
    <Marker
      key={lake.id}
      position={{
        lat: lake.coordinates.latitude,
        lng: lake.coordinates.longitude,
      }}
      onClick={handleMarkerClick}
    >
      {fishList?.length && isSelected && (
        <InfoWindow>
          <div className="capitalize">
            <h2 className="text-2xl font-semibold mb-2">{lake.name}</h2>
            <ul className="flex flex-col gap-2 px-2 font-semibold text-lg">
              {fishList.map((fish) => (
                <li key={fish.id}>{fish.name}</li>
              ))}
            </ul>
          </div>
        </InfoWindow>
      )}
    </Marker>
  );
};

async function fetchFishList(lake: any, setFishList: React.Dispatch<any[]>) {
  const url = `${SERVER_URL}${lake.fishUrl}`;

  try {
    const response = await axios.get(url);
    setFishList(response.data);
  } catch (error) {
    console.error(error);
  }
}

export default LakeMarker;
