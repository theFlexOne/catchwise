export function formatKeyString(keyString) {
  return keyString
    .split(/[ _-]/)
    .map((word, i) => {
      if (i === 0) {
        return word.toLowerCase();
      }
      return word.charAt(0).toUpperCase() + word.slice(1).toLowerCase();
    })
    .join("");
}
