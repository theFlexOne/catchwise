import fs from "fs";

const lakes = JSON.parse(fs.readFileSync("./data/lakes.json"));

const addTypePropToLakeData = (lakes) => {
  lakes = [...lakes];
  let currentLake;
  for (const lake of lakes) {
    let type;
    let parentId;

    if (lake.localId === "") {
      console.log("empty localId", lake);
      continue;
    }

    const typeCode = lake.localId.slice(-1);

    if (typeCode === "0") {
      currentLake = lake;
      type = "lake";
      parentId = lake.localId;
    } else if (currentLake.localId.slice(0, -1) === lake.localId.slice(0, -1)) {
      type = "lake_part";
      parentId = currentLake.id;
    } else {
      type = "other";
      parentId = lake.localId;
    }
    lake.type = type;
    lake.parentId = parentId;
    lake.newCountyId = formatCountyId(lake.countyId);
    delete lake.county;
    delete lake.state;
  }
  return lakes;
};

addTypePropToLakeData(lakes);

fs.writeFileSync("./data/newLakes.json", JSON.stringify(lakes, null, 2));

function formatCountyId(id, stateId = 23) {
  return `${stateId}${id.toString().padStart(3, "0")}`;
}
