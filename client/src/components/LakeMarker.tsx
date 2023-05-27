import { Marker, InfoWindow } from "@react-google-maps/api";
import axios from "axios";
import { useState } from "react";
import { FieldValues } from "../enums/FieldValues";
import buildUrl from "../helpers/buildUrl";
import { Lake } from "../types/Lake";

const API_KEY = import.meta.env.VITE_GOOGLE_API_KEY as string;

const SERVER_URL = import.meta.env.VITE_SERVER_URL as string;
// const DETAILS_URL = "https://maps.googleapis.com/maps/api/place/details/json";
const PLACES_SEARCH_URL =
  "https://maps.googleapis.com/maps/api/place/findplacefromtext/json" as string;

interface LakeMarkerProps {
  lake: any;
  children?: React.ReactNode;
  onClick?: (lake: any) => void;
  isSelected?: boolean;
}

const LakeMarker = ({ lake, onClick, isSelected }: LakeMarkerProps) => {
  const [fishList, setFishList] = useState<any[] | null>(null);
  const [googleLakeDetails, setGoogleLakeDetails] = useState<any | null>(null);

  const handleMarkerClick = async () => {
    console.log(lake);

    if (onClick) onClick(lake);

    if (fishList == null) {
      const fishList = await fetchFishList(lake);
      setFishList(fishList);
    }
    if (googleLakeDetails == null) {
      const lakeDetails = await fetchGoogleLakeDetails(lake);
      setGoogleLakeDetails(lakeDetails);
    }
  };

  googleLakeDetails && console.log(googleLakeDetails);

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

async function fetchFishList(lake: Lake) {
  const url = buildUrl(`${SERVER_URL}/api/v1/lakes/${lake.id}/fish`);

  try {
    const response = await axios.get(url);
    return response.data;
  } catch (error) {
    console.error(error);
  }
}

async function fetchGoogleLakeDetails(lake: Lake) {
  const fields = [FieldValues.place_id];

  const params = {
    input: `lake ${lake.name}, ${lake.county}`,
    inputtype: "textquery",
    key: API_KEY,
  };

  const url = buildUrl(PLACES_SEARCH_URL, params);

  try {
    const response = await axios.get(url);
    return response.data;
  } catch (error) {
    console.error(error);
  }
}

export default LakeMarker;
