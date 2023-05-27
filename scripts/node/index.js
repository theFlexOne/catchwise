import fs from "fs";

function listAllLakeCounties() {
  const lakeData = JSON.parse(fs.readFileSync("./data/serverLakeData.json"));

  const countyNames = new Set(
    lakeData.map((lake) => lake.county.toLowerCase())
  );
  console.log(countyNames);
}

function getAllFishFamilies() {
  const fishFamilySet = new Set();
  const fishData = JSON.parse(fs.readFileSync("./data/fishData.json"));
  fishData.forEach((fish) => {
    fishFamilySet.add(fish.family);
  });
  console.log(fishFamilySet);
}

getAllFishFamilies();
