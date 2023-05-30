import fs from "fs";

const lakes = JSON.parse(fs.readFileSync("./data/newNewFormattedlakes.json"));
const oldLakes = JSON.parse(fs.readFileSync("./data/newFormattedlakes.json"));

function go(lakes) {
  const newLakes = [];

  lakes.forEach((lake) => {
    const newLake = { ...lake };

    const newFishList = oldLakes
      .find((oldLake) => oldLake.localId === lake.localId)
      .fish.reduce(formatFish, []);

    newLake.fish = newFishList;

    const children = lake.children.map((child) => {
      const newChild = { ...child };
      const newFishList = oldLakes.find((oldLake) =>
        oldLake.children
          .find((c) => c.localId === child.localId)
          .fish.reduce(formatFish, [])
      );
      newChild.fish = newFishList;
      return newChild;
    });
    newLake.children = children;

    newLakes.push(newLake);
    return;
  });

  console.log(newLakes[69]);
  fs.writeFileSync(
    "./data/newNewNewFormattedlakes.json",
    JSON.stringify(newLakes, null, 2)
  );
}

function formatFish(fishes, fish) {
  if (fish.species == null) return fishes;
  if (fish.species === "noturus gyrinoturus gyrinusnus")
    fish.species = "noturus gyrinus";
  const speciesList = fish.species
    .split(" x ")
    .map((s) => s.slice(0, 1).toUpperCase() + s.slice(1));
  const newFish = { ...fish };
  newFish.fish = speciesList;
  fishes.push(newFish);
  return fishes;
}

go(lakes);
