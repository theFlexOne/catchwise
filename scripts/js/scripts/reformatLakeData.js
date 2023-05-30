import fs from "fs";

function reformatLakeData() {
  const lakes = JSON.parse(fs.readFileSync("./data/newLakes.json"));

  // console.log(lakes.length);
  // console.log(lakes[0]);

  const allLakes = lakes.reduce(
    (acc, cur) => {
      const id = cur.localId;

      delete cur.parentId;
      delete cur.type;

      cur.name = cur.name.replace("unnamed", "");
      if (cur.coordinates.latitude === 0) cur.coordinates = null;

      if (id.at(-1) === "0") {
        cur.children = [];
        acc.parents.push(cur);
      } else {
        acc.children.push(cur);
      }
      return acc;
    },
    { children: [], parents: [] }
  );

  const orphans = {};
  const newData = [...allLakes.parents];
  allLakes.children.forEach((child) => {
    const parentId = child.localId.slice(0, -1) + "0";
    const parent = newData.find((parent) => parent.localId === parentId);
    if (!parent) {
      orphans[parentId] ||= [];
      orphans[parentId].push(child);
      return;
    }
    parent.children.push(child);
  });

  fs.writeFileSync("./data/orphans.json", JSON.stringify(orphans, null, 2));
  fs.writeFileSync("./data/newNewLakes.json", JSON.stringify(newData, null, 2));
}

function removePropsFromLakes() {
  const lakes = JSON.parse(fs.readFileSync("./data/formattedLakes.json"));

  console.log(lakes[0]);

  const newLakes = lakes.map((lake) => {
    lake.children.reduce((acc, cur) => {
      const fishList = cur.fishSpecies.reduce((fishes, fish) => {
        if (fish.species == null) return fishes;
        fishes.push(fish.species);
        return fishes;
      }, []);
      const newLakeChild = {
        localId: cur.localId,
        name: cur.name,
        countyId: cur.countyId,
        coordinates: { ...cur.coordinates },
        fish: fishList,
      };
      acc.push(newLakeChild);
      return acc;
    }, []);
    const newLake = {
      localId: lake.localId,
      name: lake.name,
      countyId: lake.countyId,
      coordinates: lake.coordinates ? { ...lake.coordinates } : null,
      fish: lake.fishSpecies.reduce((acc, cur) => {
        if (cur.species == null) return acc;
        acc.push(cur.species);
        return acc;
      }, []),
      children: formatChildren(lake.children),
    };
    return newLake;
  });

  fs.writeFileSync(
    "./data/newNewFormattedLakes.json",
    JSON.stringify(newLakes, null, 2)
  );
}

function formatChildren(children) {
  return children.reduce((acc, cur) => {
    const fishList = cur.fishSpecies.reduce((fishes, fish) => {
      if (fish.species == null) return fishes;
      fishes.push(fish.species);
      return fishes;
    }, []);

    const newLakeChild = {
      localId: cur.localId,
      name: cur.name,
      coordinates: { ...cur.coordinates },
      fish: fishList,
    };
    acc.push(newLakeChild);
    return acc;
  }, []);
}

removePropsFromLakes();
