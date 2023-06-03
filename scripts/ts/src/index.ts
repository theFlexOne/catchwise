import fs from "fs";
import { Lake } from "./index.d";
import capitalize from "./helpers/capitalize";



async function main() {
  const countyData = JSON.parse(
    fs.readFileSync("./data/countyData.json", "utf8")
  );

  const counties: County[] = countyData.map(
    (county: CountyData): County => ({
      id: county.ID,
      name: county.NAMELSAD,
      stateName: county.STATE_NAME,
      stateAbbr: county.STUSPS,
      stateFP: county.STATEFP,
      countyFP: county.COUNTYFP,
      geoId: county.GEOID,
      landArea: county.ALAND,
      waterArea: county.AWATER,
    })
  );

  fs.writeFileSync("./data/counties.json", JSON.stringify(counties, null, 2));
}

main();

type County = {
  stateFP: string;
  countyFP: string;
  geoId: string;
  name: string;
  stateName: string;
  stateAbbr: string;
  landArea: number;
  waterArea: number;
  id: number;
};

interface CountyData {
  STATEFP: string; // <-- 2 digit FIPS state code
  COUNTYFP: string; // <-- 3 digit FIPS county code
  COUNTYNS: string; // <-- ANSI code
  AFFGEOID: string; // American FactFinder geoid
  GEOID: string; // <-- 5 digit FIPS code (STATEFP + COUNTYFP)
  NAME: string; // <-- County name (short)
  NAMELSAD: string; // <-- County name (long)
  STUSPS: string; // <-- State abbreviation
  STATE_NAME: string; // <-- State name
  LSAD: string; // <-- U.S. Census Bureau’s legal/statistical area description (LSAD) code for the county
  ALAND: number; // <-- Land area (square meters)
  AWATER: number; // <-- Water area (square meters)
  ID: number; // <-- Unique ID (custom)
}
