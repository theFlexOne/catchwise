import axios from "axios";
import { JSDOM } from "jsdom";
import fs from "fs";

const URL_ORIGIN = "https://www.takemefishing.org";
const URL_PATHNAME = "freshwater-fishing/types-of-freshwater-fishing";

const LINK_PATHS = [
  "lakes-and-ponds",
  "river-fishing",
  "lake-fishing",
  "pond-fishing",
  "reservoirs-and-flowages",
];

export default async function scrapeTypesOfFreshwaterFishing() {
  const url = new URL(URL_PATHNAME + "/" + LINK_PATHS[0], URL_ORIGIN);
  scrapeFreshwaterFishingType(url.toString());
}

async function scrapeFreshwaterFishingType(url) {
  try {
    const { data } = await axios.get(url);
    buildFreshwaterFishingType(data);
  } catch (err) {
    console.error(err);
  }
}

function buildFreshwaterFishingType(html) {
  const document = new JSDOM(html).window.document;

  const fishingTypeObject = new Section(
    document.querySelector("h1").textContent
  );

  const mainContent = document.querySelector(".detail-content");

  const sectionHeadingIndices = [];

  [...mainContent.children].forEach(
    (child, i) => child.tagName === "H2" && sectionHeadingIndices.push(i)
  );

  const sectionEls = sectionHeadingIndices.reduce((acc, cur, i, arr) => {
    const heading = mainContent.children[cur].textContent;
    const endIdx =
      i + 1 === arr.length ? mainContent.children.length : arr[i + 1];
    const firstSubSectionIdx = [...mainContent.children].findIndex(
      (child) => child.tagName.toUpperCase() === "H3"
    );
    const sectionIntroContent = [...mainContent.children]
      .slice(cur + 1, firstSubSectionIdx)
      .reduce((acc, cur) => {
        const contentObject = {
          tag: cur.tagName,
          text: cur.textContent,
        };
        acc.push(contentObject);
        return acc;
      }, []);

    const sectionContent = [...mainContent.children].slice(cur + 1, endIdx);
    acc.push({ heading, sectionContent });
    return acc;
  }, []);

  // console.log(sectionEls);
  scrapeHTMLContent([...mainContent.children]);

  return fishingTypeObject;
}

function buildSection(sectionContent) {
  const subSectionHeadingIndices = [];

  sectionContent.forEach((child, i) => {
    return (
      child.tagName.toUpperCase() === "H2" && sectionHeadingIndices.push(i)
    );
  });
}

function sliceHTML(html, start, end, sliceAll = false) {
  html = new JSDOM(html);
  start = html.querySelector(start);
}

function scrapeElementList(elements, heading = "") {
  const content = { heading, sections: [] };

  [...elements].forEach((el) => {
    const hMatches = el.tagName.matchAll(/H([2-6])/);
    console.log(hMatches);
    if (hMatches.length > 0) {
      const section = {
        heading: el.textContent,
        sections: [],
      };
      content.sections.push(section);
    }
  });
}

function scrapeHTMLContent(
  childrenList = [...window.HTMLBodyElement.children]
) {
  const content = [];
  childrenList.forEach((child) => {
    const section = new Section();
    [...child.children].forEach((el) => {
      section.addSection([...el.children]);
    });
    content.push(section);
  });
  console.log(content);
  return content;
}

class Section {
  constructor(heading = "", textList = [], sections = []) {
    this.heading = heading;
    this.textList = textList;
    this.sections = sections;
  }

  appendText(text) {
    this.textList.push(text);
  }

  addSection(section) {
    this.sections.push(section);
  }
}
