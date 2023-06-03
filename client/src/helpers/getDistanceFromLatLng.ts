import haversine from "haversine";
import { Unit } from "../enums/Unit";

export default function getDistanceFromLatLng(
  start: LatLngLiteral,
  end: LatLngLiteral,
  unit: Unit = Unit.KM
): number {
  const options = {
    unit,
  };

  return haversine(
    { latitude: start.lat, longitude: start.lng },
    { latitude: end.lat, longitude: end.lng },
    options
  );
}

type LatLngLiteral = google.maps.LatLngLiteral;
