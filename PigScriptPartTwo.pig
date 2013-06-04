/*
 * RONALD INSELBERG
 * Assignment Three, Part Two
 *
 * Pig script to produce, for each actor, the number of movies that actor was cast in.
 * The output is sorted by the number of movies, in descending order.
 *
 * The concept is identical to the Java MapReduce program of Assignment Two, Part Two, but the
 * output data is not sorted in exactly the same order, as explained in READMEforPigScriptPartOne.
 *
 *
 * Read input data in tsv ( tab-separated values) format. Record fields are actor, movie and year.
 * USING PigStorage('\t') specifies that data fields are seperated by tab characters; this is explicit.
 * Could have omitted this and it would work the same by default.
 */
movieYearAndWeight = LOAD 'imdb-weights.tsv' USING PigStorage('\t') AS (movie:chararray, year:int, weight:float);
movieYearAndWeight = FILTER movieYearAndWeight BY movie IS NOT NULL;
movieYearAndWeight = FILTER movieYearAndWeight BY year IS NOT NULL;
movieYearAndWeight = FILTER movieYearAndWeight BY weight IS NOT NULL;
groupByYear = GROUP movieYearAndWeight BY year;
sortByWeight = FOREACH groupByYear {
  sorted = ORDER movieYearAndWeight BY weight DESC;
	GENERATE group, sorted;
};
highestRankedMovie = FOREACH sortByWeight {
	highest = LIMIT sorted 1;
	GENERATE group, highest;
};
highestRankedMovie = FOREACH highestRankedMovie GENERATE group, FLATTEN(highest) AS (movieName:chararray, movieYear:int, movieWeight:float);
actorMovieAndYear = LOAD 'imdb.tsv' USING PigStorage('\t') AS (actorName:chararray, movieTitle:chararray, yearReleased:int);
xData = COGROUP highestRankedMovie BY movieName, actorMovieAndYear BY movieTitle;
--yData = FOREACH xData GENERATE group, . . .
DESCRIBE xData;
--DESCRIBE yData;
--xData: {group: chararray,highestRankedMovie: {(group: int,movieName: chararray,movieYear: int,movieWeight: float)},actorMovieAndYear: {(actorName: chararray,movieTitle: chararray,yearReleased: int)}}
dumpRecords = LIMIT groupByYear 10;
DUMP dumpRecords;
dumpRecords2 = LIMIT sortByWeight 10;
DUMP dumpRecords2;
dumpRecords3 = LIMIT highestRankedMovie 10;
DUMP dumpRecords3;
dumpRecords4 = LIMIT xData 10;
DUMP dumpRecords4;
--dumpRecords5 = LIMIT yData 10;
--DUMP dumpRecords5;

