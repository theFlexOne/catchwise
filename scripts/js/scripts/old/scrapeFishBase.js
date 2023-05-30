import fs from "fs";
import { JSDOM } from "jsdom";
import axios from "axios";
import { formatKeyString } from "./helpers.js";

const url = new URL(
  "https://www.fishbase.se/country/CountryChecklist.php?showAll=yes&what=list&trpp=50&c_code=840&cpresence=present&sortby=alpha2&ext_CL=on&vhabitat=all2"
);

const fetchFishTableHtml = async () => {
  const { data } = await axios.get(url.href);
  const dom = new JSDOM(data);
  const table = dom.window.document.querySelector("table.commonTable");
  return table.outerHTML;
};

const htmlToJson = () => {
  const html = fs.readFileSync("./html/fishBase.html", "utf8");
  const dom = new JSDOM(html);
  const table = dom.window.document.querySelector("table.commonTable");
  const headers = [...table.querySelectorAll("thead th")].map(
    (th) => th.textContent
  );
  const data = [...table.querySelectorAll("tbody tr")].map((tr) => {
    const tds = [...tr.querySelectorAll("td")];
    const rawData = headers.reduce((acc, header, index) => {
      acc[formatKeyString(header)] = tds[index].textContent.trim();
      return acc;
    }, {});
    return {
      family: rawData.family,
      species: rawData.species,
      commonNames: rawData.commonNames,
      info: rawData.info,
      occurence: rawData.occurence,
      abundance: rawData.abundance,
      maxLength: rawData.maxLength,
      maturity: rawData.maturity,
    };
  });
  fs.writeFileSync("data/fishBaseFishList.json", JSON.stringify(data, null, 2));
};

export default () => {
  const fishes = JSON.parse(
    fs.readFileSync("./data/fishBaseFishList.json", "utf8")
  );
  const freshwaterFish = fishes.filter((fish) => {
    const infoCodes = fish.info.split(/,? /).map((code) => code.toUpperCase());
    return infoCodes.includes("FR");
  });
  const commonFreshwaterFish = freshwaterFish.filter((fish) =>
    fish.abundance.includes("common")
  );

  const abundanceTypes = new Set();
  freshwaterFish.forEach((fish) => {
    const index = fish.abundance.indexOf("(");
    const type = fish.abundance.slice(0, index).trim();
    type && abundanceTypes.add(type);
  });
  const abundanceTypesArray = [...abundanceTypes].sort();
  console.log(abundanceTypesArray);
};
