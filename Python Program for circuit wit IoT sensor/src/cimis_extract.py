from lib.cimis import cimis
#from matplotlib import pyplot as plt

def extractData():
    appKey = 'acac78e2-860f-4194-b27c-ebc296745833'  # cimis unique appKey
    sites = [80]  #query single site or multiple

    ## will need to change this depending on the path 
    xls_path = 'CIMIS_query.xlsx' # TODO: make this dep on stations/query date

    interval ='hourly' #options are:    default, daily, hourly
    start = '10-01-2014' #self-explanatory
    end = '03-01-2017' #self-explanatory
    param  = 'DayEto'
    param1  = 'DayPrecip'

    #get the data as a list of data frames; one dataframe for each site
    site_names, cimis_data = cimis.run_query(appKey, sites, interval, start=start, end=end)

    #write queried data to excel file
    cimis.write_output_file(xls_path, cimis_data, site_names)


if __name__ == "__main__":
    extractData()
