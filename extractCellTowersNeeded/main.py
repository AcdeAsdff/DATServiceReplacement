import math,csv

def parseRow(row):
    print('addLTECellTower(%s,%s,%s,%s,%s,3745,new int[]{8},10000,"%s","%s","CHN-UNICOM","UNICOM",new ArrayList<>(),null,2560);'
          %(row[6],row[7],row[4],str(int(row[-2])%500),row[3],row[1],row[2]))
    # print(row)

centerLon = 114.32815
centerLat = 30.374547
lontiRange = 0.3
latiRange = 0.3
csvName = "460.csv"
import csv
with open(csvName, newline='') as csvfile:
    reader = csv.reader(csvfile, delimiter=' ', quotechar='|')
    for row in reader:
        row = row[0].split(',')
        # print(row)
        lonti = float(row[6])
        lati = float(row[7])
        if 'LTE' == row[0] and '460' == row[1] and '1' == row[2]:
            if (math.fabs(lonti - centerLon) <= lontiRange) and (math.fabs(lati - centerLat) <= latiRange):
                parseRow(row)

