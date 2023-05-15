import fs from "fs";

const fishData = JSON.parse(fs.readFileSync("./data/fishData.json"));
const fishBaseData = JSON.parse(
  fs.readFileSync("./data/fishBaseFishList.json")
);

console.log(fishData.length);
console.log(fishBaseData.length);

const newFishData = fishData.map((fish, i) => {
  const fishName = fish.name.toLowerCase();
  const fishBase = fishBaseData.find((fishBase) => {
    const commonNames = fishBase.commonNames.split(",").map((name) =>
      name
        .match(/^(.*)\(??/)[1]
        .trim()
        .toLowerCase()
    );
    if (i === 0) {
      console.log(fishName);
      console.log(commonNames);
    }
  });
  if (!fishBase) {
    // console.log(fishName);
    return;
  }
  const [genus, species] = fishBase.species
    .split(" ")
    .map((name) => name.match(/^.*\(?/));
  console.log(genus, species);
  return {
    ...fish,
    family,
    genus,
    species,
  };
});
