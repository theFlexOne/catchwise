import fs from "fs";

const countyData: CountyJSON[] = JSON.parse(
  fs.readFileSync(
    "../../../../../Desktop/GeoData Stuff/USCountiesGeoData.json",
    "utf8"
  )
);
const stateData: StateJSON[] = JSON.parse(
  fs.readFileSync(
    "../../../../../Desktop/GeoData Stuff/USStatesGeoData.json",
    "utf8"
  )
);

export default function main() {
  const newCountyData = countyData.map((county): County => {
    const coordinates = formatCoordinates(county.geometry.coordinates);
    return {
      ...county,
      geometry: {
        ...county.geometry,
        coordinates,
      },
    };
  });

  const newStateData = stateData.map((state): State => {
    const coordinates = formatCoordinates(state.geometry.coordinates);
    return {
      ...state,
      geometry: {
        ...state.geometry,
        coordinates,
      },
    };
  });

  writeToJSONFile(newCountyData, "countiesGeoData");
  writeToJSONFile(newStateData, "statesGeoData");
}
function writeToJSONFile(data: any, filename: string) {
  fs.writeFileSync(`./data/${filename}.json`, JSON.stringify(data, null, 2));
}
function formatCoordinates(
  coordinates: number[][][][]
): { latitude: number; longitude: number }[] {
  return coordinates
    .map(
      (c: any) =>
        (c = c
          .flat()
          .map(([lng, lat]: number[]) => ({ latitude: lat, longitude: lng })))
    )
    .flat(Infinity);
}

export interface County {
  countyfp: string;
  countyns: string;
  affgeoid: string;
  geoid: string;
  name: string;
  lsad: string;
  aland: number;
  awater: number;
  geometry: Geometry;
}
export interface State {
  statefp: string;
  statens: string;
  affgeoid: string;
  geoid: string;
  sTUSPS: string;
  name: string;
  lsad: string;
  aland: number;
  awater: number;
  geometry: Geometry;
}
export interface Geometry {
  type: string;
  coordinates: { latitude: number; longitude: number }[];
}

export interface CountyJSON {
  countyfp: string;
  countyns: string;
  affgeoid: string;
  geoid: string;
  name: string;
  lsad: string;
  aland: number;
  awater: number;
  geometry: GeometryJSON;
}

export interface StateJSON {
  statefp: string;
  statens: string;
  affgeoid: string;
  geoid: string;
  sTUSPS: string;
  name: string;
  lsad: string;
  aland: number;
  awater: number;
  geometry: GeometryJSON;
}

export interface GeometryJSON {
  type: string;
  coordinates: number[][][][];
}
