/*
Steven Prine
CSC-346
Prof. Noynaert
*/

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

//The parks object
class Parks {
    def state
    def park
    def year

    Parks(state, park, year) {
        this.state = state
        this.park = park
        this.year = year
    }

    @Override
    String toString() {
        return "State: $state  Park:  $park  Year $year"
    }
}

def searchForWebsite() {

    try {
        def website = "https://www.nationalpark-adventures.com/united-states-national-parks.html"

        //Setting up Jsoup to be able to pull in the html document
        Document document = Jsoup.connect(website).get();
        title = document.title()
        println "The title is: $title \n"

        Elements rows = document.select("tbody > tr")
        rows.remove(0)
        def state
        def name
        def year
        Parks parks

        println "Please Enter a Target Phrase"
        def targetPhrase = System.in.newReader().readLine()

        def search = []
        rows.each { row ->
            def cells = row.select("td")

            if (cells.size() == 3) {
                state = cells[0].text()
                state = state.replaceAll(/\s+\(\d+\)/, "")
                name = cells[1].text()
                year = cells[2].text()
                parks = new Parks(state, name, year)
            } else {
                name = cells[0].text()
                year = cells[1].text()
                parks = new Parks(state, name, year)
            }
            if (parks.toString() =~ /(?i)$targetPhrase/) {
                search.add(parks)
            }
        }
        search.each {
            println it
        }

    } catch (IO) {
        System.err.println("Website could not be reached")
    }
}

searchForWebsite()