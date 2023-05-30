import fs from "fs";

const lakeData = JSON.parse(fs.readFileSync("./data/lakeData3.json"));
const formattedLakesData = JSON.parse(
  fs.readFileSync("./data/formattedLakes.json")
);

const newLakeData = lakeData.map((l) => {
  const fish =
    formattedLakesData.find((fl) => {
      const isLake = fl.localId === l.localId;
      if (isLake) return true;
      return fl.children.find((flc) => flc.localId === l.localId);
    })?.fishSpecies || [];

  return {
    localId: l.localId,
    name: l.name,
    countyId: l.countyId,
    nearestTown: l.nearestTown,
    coordinates: l.coordinates,
    fish,
  };
});

fs.writeFileSync("./data/lakeData4.json", JSON.stringify(newLakeData, null, 2));
