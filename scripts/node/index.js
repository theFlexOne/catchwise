import fs from "fs";
import axios from "axios";

const lakeData = JSON.parse(fs.readFileSync("./data/newLakeData.json"));

const allFishSpeciesInLakeData = lakeData.reduce((acc, lake) => {
  lake.fishSpecies.forEach((fish) => {
    acc.add(fish);
  });
  return acc;
}, new Set());

// console.log(allFishSpeciesInLakeData.size);
// console.log([...allFishSpeciesInLakeData].sort());
async function getFishBaseFishIds() {
  const fishData = JSON.parse(fs.readFileSync("./data/newFishData.json"));
  const fishSpecies = fishData.map((fish) => fish.species);

  const idFetches = fishSpecies.reduce((acc, species) => {
    if (species.includes("/")) return acc;
    const url = new URL("https://www.fishbase.org.au/v4/search");
    url.searchParams.append("q", species);

    acc[0].push(axios.get(url));
    acc[1].push(species);
    return acc;
  }, [[],[]]);

  const responseList = await Promise.all(idFetches[0]);
  const idMap = responseList.reduce((acc, res, i) => {
    const { request: {path} } = res;
    const id = path.split("/").at(-1);
    const name = idFetches[1][i];
    acc.push({name, id});
    return acc;
  }, []);
  console.log(idMap);
}

getFishBaseFishIds();



// function createNewLakeData() {
//   const newLakeData = lakeData.map((lake) => {
//     return {
//       localId: lake?.id || "",
//       name: lake?.name.toLowerCase() || "",
//       county: lake?.county.toLowerCase() || "",
//       countyId: lake?.county_id || "",
//       state: "MN",
//       nearestTown: lake?.nearest_town.toLowerCase() || "",
//       coords: {
//         lat: lake?.point["epsg:4326"][0] || 0,
//         lng: lake?.point["epsg:4326"][1] || 0,
//       },
//       fishSpecies: lake?.fishSpecies || [],
//     }
//   })

//   fs.writeFileSync("./data/newLakeData.json", JSON.stringify(newLakeData, null, 2));
// }

// const fishData = JSON.parse(fs.readFileSync("./data/fishData.json"));
// const fishBaseData = JSON.parse(
//   fs.readFileSync("./data/fishBaseFishList.json")
// );
//
// function createNewFishData() {
//   const newFishData = fishData.map((fish, i) => {
//     const fishName = fish.name.toLowerCase();
//     const fishBase = fishBaseData.find((fishBase) => {
//       const commonNames = fishBase.commonNames.split(",").map((name) => {
//         const idx = name.indexOf("(");
//         if (idx > -1) {
//           name = name.slice(0, idx);
//         }
//         return name.trim().toLowerCase();
//       });
//       if (commonNames.includes(fishName)) {
//         return fishBase;
//       }
//     });
//     !fishBase && console.log(fishName);
//     return {
//       name: fish.name.toLowerCase(),
//       family: fishBase?.family.trim().toLowerCase() || "",
//       species: fishBase?.species.trim().toLowerCase() || "",
//       description: fish.description,
//       identification: fish.identification,
//     };
//   });
//   fs.writeFileSync("./data/newFishData.json", JSON.stringify(newFishData));
// }
