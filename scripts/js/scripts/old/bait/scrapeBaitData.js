import axios from "axios";
import { JSDOM } from "jsdom";
import fs from "fs";

const ROOT_URL =
  "https://www.takemefishing.org/freshwater-fishing/freshwater-bait-and-lures";

const pathnames = [
  "freshwater-bait",
  // "freshwater-lures",
  // "bass-fishing-lures",
  // "freshwater-salmon-lures",
];

const parseOutBait = (content) => {
  const headerIndexes = content.children.reduce((indexes, child, index) => {
    if (child.tagName.match(/H\d/)) {
      indexes.push(index);
    }
    return indexes;
  }, []);

  const baitList = headerIndexes.map((headerIndex, index) => {
    const header = content.children[headerIndex];
    const nextHeaderIndex = headerIndexes[index + 1];
    const html = content.children.slice(headerIndex + 1, nextHeaderIndex);
    const bait = {
      name: header.textContent,
      description: html.reduce((description, element) => {
        return description + element.textContent;
      }, ""),
    };
    return bait;
  });

  return baitList;
};

export const scrapeBaitData = async () => {
  const fetches = pathnames.map((pathname) => {
    const url = new URL(ROOT_URL);
    url.pathname = pathname;
    return axios.get(url.href);
  });

  const response = await Promise.all(fetches);
  const data = response.map((res) => {
    const dom = new JSDOM(res.data);
    const { document } = dom.window;
    const content = document.querySelector(
      ".detail-content || .content-wrap.group.content-items > div"
    );
    // console.log(content);
    return baitList;
  });
};
