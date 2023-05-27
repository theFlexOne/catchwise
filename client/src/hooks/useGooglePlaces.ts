import axios from "axios";
import { FieldValues } from "../enums/FieldValues";

const PLACES_API_KEY = import.meta.env.VITE_GOOGLE_API_KEY;
const PLACES_API_URL = "https://maps.googleapis.com/maps/api/place";

interface SearchForPlaceParams {
  fields: FieldValues[];
  input: string;
  inputType: string;
  output: "json" | "xml";
}

interface GetPlaceDetailsParams {
  fields: FieldValues[];
  placeId: string;
  output: "json" | "xml";
}

export default function useGooglePlaces() {
  async function searchForPlace({
    fields,
    input,
    inputType,
    output = "json",
  }: SearchForPlaceParams) {
    const url = new URL(`${PLACES_API_URL}/findplacefromtext/${output}`);
    const params = {
      key: PLACES_API_KEY,
      input,
      inputtype: inputType,
      fields: fields.join(","),
    };
    url.search = new URLSearchParams(params).toString();
    const response = await axios.get(url.toString());
    console.log(response);
    return response;
  }

  async function getPlaceDetails({
    fields,
    placeId,
    output = "json",
  }: GetPlaceDetailsParams) {
    const url = new URL(`${PLACES_API_URL}/details/${output}`);
    const params = {
      key: PLACES_API_KEY,
      place_id: placeId,
      fields: fields.join(","),
    };
    url.search = new URLSearchParams(params).toString();
    const response = await axios.get(url.toString());
    console.log(response);
    return response;
  }

  return {
    searchForPlace,
    getPlaceDetails,
  };
}
