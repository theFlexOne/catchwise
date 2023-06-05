import fs from "fs";
import { County, State } from "./formatCountiesAndStatesGeoJSON";

<<<<<<< HEAD


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
=======
const lakes = JSON.parse(fs.readFileSync("./data/newLakesData.json", "utf8"));

const fipCodes: { [key: string]: string } = {
  Aitkin: "27001",
  Anoka: "27003",
  Becker: "27005",
  Beltrami: "27007",
  Benton: "27009",
  "Big Stone": "27011",
  "Blue Earth": "27013",
  Brown: "27015",
  Carlton: "27017",
  Carver: "27019",
  Cass: "27021",
  Chippewa: "27023",
  Chisago: "27025",
  Clay: "27027",
  Clearwater: "27029",
  Cook: "27031",
  Cottonwood: "27033",
  "Crow Wing": "27035",
  Dakota: "27037",
  Dodge: "27039",
  Douglas: "27041",
  Faribault: "27043",
  Fillmore: "27045",
  Freeborn: "27047",
  Goodhue: "27049",
  Grant: "27051",
  Hennepin: "27053",
  Houston: "27055",
  Hubbard: "27057",
  Isanti: "27059",
  Itasca: "27061",
  Jackson: "27063",
  Kanabec: "27065",
  Kandiyohi: "27067",
  Kittson: "27069",
  Koochiching: "27071",
  "Lac qui Parle": "27073",
  "Lake of the Woods": "27077",
  Lake: "27075",
  "Le Sueur": "27079",
  Lincoln: "27081",
  Lyon: "27083",
  Mahnomen: "27087",
  Marshall: "27089",
  Martin: "27091",
  McLeod: "27085",
  Meeker: "27093",
  "Mille Lacs": "27095",
  Morrison: "27097",
  Mower: "27099",
  Murray: "27101",
  Nicollet: "27103",
  Nobles: "27105",
  Norman: "27107",
  Olmsted: "27109",
  "Otter Tail": "27111",
  Pennington: "27113",
  Pine: "27115",
  Pipestone: "27117",
  Polk: "27119",
  Pope: "27121",
  Ramsey: "27123",
  "Red Lake": "27125",
  Redwood: "27127",
  Renville: "27129",
  Rice: "27131",
  Rock: "27133",
  Roseau: "27135",
  "Saint Louis": "27137",
  Scott: "27139",
  Sherburne: "27141",
  Sibley: "27143",
  Stearns: "27145",
  Steele: "27147",
  Stevens: "27149",
  Swift: "27151",
  Todd: "27153",
  Traverse: "27155",
  Wabasha: "27157",
  Wadena: "27159",
  Waseca: "27161",
  Washington: "27163",
  Watonwan: "27165",
  Wilkin: "27167",
  Winona: "27169",
  Wright: "27171",
  "Yellow Medicine": "27173",
};

lakes.forEach((lake: any) => {
  lake.countyFips = fipCodes[lake.countyName];
});

fs.writeFileSync("./data/newLakesData.json", JSON.stringify(lakes, null, 2));

function combineStatesAndCounties() {
  const states: State[] = JSON.parse(
    fs.readFileSync("./data/statesGeoData.json", "utf8")
  );
  const counties: County[] = JSON.parse(
    fs.readFileSync("./data/countiesGeoData.json", "utf8")
  );

  const newStatesData = states.map((state) => {
    const stateCounties = counties.filter(
      (county) => county.geoid.slice(0, 2) === state.geoid
    );
    return {
      ...state,
      counties: stateCounties,
    };
  });

  fs.writeFileSync(
    "./data/statesWithCountiesGeoData.json",
    JSON.stringify(newStatesData, null, 2)
  );

  console.log("Done!");
>>>>>>> 704d6b66c9d39fa127599ed7bea093d15f86b9d3
}
