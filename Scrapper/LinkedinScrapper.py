""" filename: script.py """

from time import sleep, time
import json,csv
from parsel import Selector
from selenium import webdriver
from selenium.webdriver.remote.webelement import WebElement
from selenium.webdriver.support.wait import WebDriverWait

import parameters

def writeNodesToFile(map):
    with open('nodes.json', 'w') as outfile:
            json.dump(map, outfile)

def scroll_down(driver):
    """A method for scrolling the page."""
    # Get scroll height.
    last_height = driver.execute_script("return document.body.scrollHeight")
    while True:
        # Scroll down to the bottom.
        driver.execute_script("window.scrollTo(0, document.body.scrollHeight);")
        # Wait to load the page.
        sleep(2)
        # Calculate new scroll height and compare with last scroll height.
        new_height = driver.execute_script("return document.body.scrollHeight")
        if new_height == last_height:
            break
        last_height = new_height

def writeNodes(list_of_jsons):
    map={}
    for json in list_of_jsons:
        map[json['linkedin_url']]=json

def writeEdges(tuplePairs):
    with open('edges.csv', mode='w') as edges_file:
        edges_writer = csv.writer(edges_file, delimiter=',', quotechar='"', quoting=csv.QUOTE_MINIMAL)
        for x in tuplePairs:
            edges_writer.writerow([x[0], x[1]])

def dataScrapper(driver,first:bool):
    # assigning the source code for the webpage to variable sel
    sel = Selector(text=driver.page_source)
    # xpath to extract the text from the class containing the name
    name = sel.xpath('//*[starts-with(@class, "pv-top-card-section__name")]/text()').extract_first()
    if name:
        name = name.strip()
    # xpath to extract the text from the class containing the job title
    job_title = sel.xpath('//*[starts-with(@class, "pv-top-card-section__headline")]/text()').extract_first()

    if job_title:
        job_title = job_title.strip()
    # xpath to extract the text from the class containing the company
    company = sel.xpath('//*[starts-with(@class, "pv-top-card-v2-section__entity-name pv-top-card-v2-section__company-name")]/text()').extract_first()
    if company:
        company = company.strip()


    # xpath to extract the text from the class containing the college
    college = sel.xpath('//*[starts-with(@class, "pv-top-card-v2-section__entity-name pv-top-card-v2-section__school-name")]/text()').extract_first()

    if college:
        college = college.strip()
    # xpath to extract the text from the class containing the location
    location = sel.xpath('//*[starts-with(@class,  "pv-top-card-section__location")]/text()').extract_first()
    if location:
        location = location.strip()
    linkedin_url = driver.current_url
    # printing the output to the terminal
    json_object = {}
    print('\n')
    print('Name: ' + name)
    json_object['name'] = name
    print('Job Title: ' + job_title)
    json_object['job_title'] = job_title
    if first:
        image= driver.find_element_by_css_selector('.profile-photo-edit__preview').get_attribute('src')
    else:
        image = driver.find_element_by_css_selector('.pv-top-card-section__photo.presence-entity__image.EntityPhoto-circle-9.ember-view').get_attribute('style').replace('background-image: url("', '').replace('");', '')

    if company != None:
        json_object['company'] = company
        print('Company: ' + company)
    if college != None:
        json_object['college'] = college
        print('College: ' + college)
    print('Location: ' + location)
    json_object['location'] = location
    print('URL: ' + linkedin_url)
    json_object['linkedin_url'] = linkedin_url
    json_object['image'] = image
    print('\n')
    return json_object

def listContactUrls(list:WebElement):
    urls=[]
    count=0
    for x in list:
        if count < 60:
            link = x.get_attribute('href')
            urls.append(link)
            count += 1
        else:
            return urls
    return urls




# ---------------------------------main--------------------------#


driver = webdriver.Chrome('/Users/doronm/chromedriver')
wait =  WebDriverWait(driver, 10);

driver.get('https://www.linkedin.com')
driver.maximize_window()
username = driver.find_element_by_class_name('login-email')
username.send_keys(parameters.linkedin_username)
sleep(0.5)

password = driver.find_element_by_class_name('login-password')
password.send_keys(parameters.linkedin_password)
sleep(0.5)

sign_in_button = driver.find_element_by_xpath('//*[@type="submit"]')
sign_in_button.click()
sleep(5)
list_of_jsons_map ={}

#first user
first_profile_link = driver.find_element_by_xpath('//*[@id="voyager-feed"]/div/div/aside/div/div/div/a').get_attribute('href')
driver.get(first_profile_link)
sleep(3)
first_ptofile_data = dataScrapper(driver, True)
firstkey=first_ptofile_data['linkedin_url']
firstkey=firstkey[28:-1]
list_of_jsons_map[firstkey] =first_ptofile_data

my_network = driver.find_element_by_xpath('//*[@id="mynetwork-nav-item"]/a/span[2]')
my_network.click()
wait.until(lambda driver: driver.find_element_by_css_selector('a[data-control-name="connections"]'))
my_connection = driver.find_element_by_css_selector('a[data-control-name="connections"]')
my_connection.click()
sleep(1)

# scroll_down(driver)
list_of_contacts = driver.find_elements_by_xpath('//*[@class="core-rail"]/div/div/section/ul/li/div/div/a')
first_tier_list_of_contacts_url =listContactUrls(list_of_contacts)
list_of_edges_pairs = []
for name in first_tier_list_of_contacts_url:
    driver.get(name)
    sleep(5)
    js=dataScrapper(driver,False)
    key=js['linkedin_url']
    key=key[28:-1]
    list_of_edges_pairs.append((firstkey, key))
    list_of_jsons_map[key] =js
writeNodesToFile(list_of_jsons_map)
writeEdges(list_of_edges_pairs)

print()


sleep(0.5)

driver.quit()


# I will provide you code in Python for this. I think it's easy to translate to Java:

