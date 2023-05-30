export default function capitalize(str: string): string {
  return str
    .split(" ")
    .map((word) => {
      const firstLetterIndex = word.match(/\w/)?.index || 0;
      return (
        word.slice(0, firstLetterIndex) +
        word.slice(firstLetterIndex, firstLetterIndex + 1).toUpperCase() +
        word.slice(firstLetterIndex + 1).toLowerCase()
      );
    })
    .join(" ");
}
