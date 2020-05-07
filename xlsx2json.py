# xlsx2json.py
# ---------

"""
xlsx2json.py reads excel sheets from '/ExcelSheets/' with query information and converts to json file
root_dir \
        xlsx2json.py
        ExcelSheets \
                Events.xlsx
                News.xlsx
        json \
                Events.xlsx
                News.xlsx
"""
import xlrd
import os
from collections import OrderedDict
import simplejson as json

script_dir = (os.getcwd())  # getting file directory path
script_len = len(script_dir)  # finding the length of directory path

sources = ['Events', 'News']

for i in range(len(sources)):

    featureCollection = OrderedDict()
    featureCollection['type'] = 'QueryCollection'
    queries = []

    wb = xlrd.open_workbook(script_dir + '/ExcelSheets/' + sources[i] + '.xlsx')
    for j in range(len(wb.sheet_names())):
        sh = wb.sheet_by_index(j)

        query = OrderedDict()
        query['type'] = 'Query'

        parameters = []
        
        for rownum in range(sh.nrows):
            
            row_values = sh.row_values(rownum)

            parameter = OrderedDict()

            parameter['name'] = row_values[0]
            parameter['value'] = row_values[1]

            parameters.append(parameter)
        
        query['Parameters'] = parameters
        queries.append(query)

    featureCollection['queries'] = queries

    # Serialize the list of dicts to JSON
    j = json.dumps(featureCollection)
    # Write to file
    with open(script_dir + '/json/' + sources[i] + '.json', 'w') as f:
        f.write(j)

    


