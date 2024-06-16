# CMPT 276 Project Proposal

**Group 7:** 	Anastasiia Kim,  Andy Zeng, Belinda Zhu, Devin Dang, Utsav Patel\
**Github:** 	nz0201, AH134, SpringOions, devindang7, utsav7123\
**GITHUB:** https://github.com/AH134/cmpt276-group-project/

**Potential names**
- Adopt a plant
- Grow with you

## Initial description of our project
&nbsp;&nbsp;&nbsp;&nbsp;An application which offers two types of user logins: "Grower" and "Sponsor”. "Growers" are individuals with extra space in their backyard or farms who wish to grow plants and earn money by doing so. Growers can list their available spaces and manage the plants they grow. "Sponsors" are Individuals who want to grow a plant but lack sufficient space or skills to do so. Sponsors can select a Grower and pay them on a biweekly basis to take care of their plants. Sponsors will receive at least weekly updates on plants that they sponsor. Updates can be more frequent if both Sponsor and the Grower agree. Additionally, the application will have a marketplace where people may list plants to be traded/sold. The application will also have informational resources on identifying and taking care of plants.

## The problem we are solving
&nbsp;&nbsp;&nbsp;&nbsp;Entertainment will make life better for people who can’t take care of plants. The lack of space for plant cultivation presents a number of difficulties for urban gardening enthusiasts, who are referred to as "Sponsors" in our application. These people love to garden and would love to feel the satisfaction of raising plants, but their living circumstances prevent them from doing so. As things stand, these Sponsors might turn to restricted options like indoor gardening, which limits the kinds and quantity of plants they can cultivate.

## Target audience
&nbsp;&nbsp;&nbsp;&nbsp;Our web application bridges this gap by connecting Sponsors with Growers—individuals with extra backyard or farm space. This collaborative model allows Growers to earn money by offering their available space and gardening expertise, while Sponsors can remotely manage and enjoy the growth of their plants.

## Login
&nbsp;&nbsp;&nbsp;&nbsp;One login is the Admin login. After logging in, they can see all users and can delete accounts that may have inappropriate content.
Another login is for regular users. After login growers and sponsors can create a profile. Sponsors can pay for plants on the marketplace. Growers can post plants that need sponsorship on the marketplace. Both have access to more features that are listed below in this document.

## Potential API’s
&nbsp;&nbsp;&nbsp;&nbsp;Some potential APIs we may use: Weather API (https://openweathermap.org/api), AI plant identifier (https://web.plant.id/plant-identification-api/), Plant growing information API (https://trefle.io/), Plant documentation API (https://perenual.com/docs/api), Benefits of your tree (https://mytree.itreetools.org/#/ ), and Google Maps API. The Weather API will be used for updates on weather for growers and sponsors. The AI plant identifier can be used to help identify plants. Plant growing information API, Plant documentation API and Benefits of your tree will be used for educational information. The Google Maps API will list out nearby gardening stores.
	We will use the The Plant documentation API as our Primary API. The Plant documentation API provides access to a wealth of plant-based information, including species, care guides, growth stages, images, hardiness zones and much more.

## Scope of our project
&nbsp;&nbsp;&nbsp;&nbsp;Users can make profiles for themselves that contain profiles of plants. Ratings for plant care and comments on plants will be available. Plant information and plant identification will be available on the plant profile through API’s. Some examples would be the benefits of the plant that you own or growing information. Users will also be able to follow each other.
Ability to make profiles for the plants with sponsoring the plant as an option and trading/selling/exchanging plants as an option. Plants that are sponsored may only have one sponsor.
A marketplace will also be available where you can search and filter for new plants to buy or sponsor. We will probably be implementing fake money for now.
Additionally, there can be a leaderboard function with rewards. Some leaderboards would be who has the most sponsored plants, the happiest plants, and the most plants raised. Rewards would be coupons for gardening supplies or free gardening supplies.
People who sponsor plants will also require weekly updates at least. They may also be able to have more frequent updates by requesting the Grower. These updates will be shown through a feed.
A chat will also be required so Growers and Sponsors are able to communicate. It may also be necessary for the marketplace when people buy/trade plants.
Some fruit species need to be pollinated by the male plants or other varieties of the same species to start producing fruit. We will create a “plant tinder”/”plant marketplace” This can help connect people to suppliers that have male plants/other species of plants for pollination.
Using Google API, local stores can be suggested to Growers for buying plants and gardening supplies. 

## Stories
**Updates on a plant (Grower)**
- Sponsor would like an update on how the plant is doing every week.
- Trigger: 1 week has passed since the last week.
- Action: Send an email/message to the grower to remind him to send a picture of the plant.
- Tests: time > 1 week, image has been uploaded.

**Update on a plant (Sponsor)**
- Sponsor would like to receive weekly photo updates from Grower.
- Trigger: 1 week has passed since last week.
- Action: Nudge button option to remind the grower to send a picture update of the plant.
- Tests: time > 1 week (can nudge only once a week).

**Plant profile creation**
- Grower wishes to create a profile for each of her plants to get a sponsor for the plant.
- Trigger: Grower logs into her account.
- Action: Grower is prompted to fill in various details, including the plant's name, type, current picture and the approximate date it was planted.
- Tests: Form Submission Process - Verify that the form submission process works correctly and captures all necessary details.

**Paying for a plant**
- Sponsor would like to buy a plant from Grower.
- Trigger: Sponsor pays Grower’s price in the marketplace.
- Action: Notify Grower that the plant has been bought. Deduct funds from Sponsor’s balance. Add funds to Grower’s balance. Add plants to Grower’s profile.
- Tests: Check both balances for decrease and increase. Check if Sponsor has enough money.


**Grower review**
- Sponsor would like to write a review about the grower they sponsored.
- Trigger: After the plant is fully grown and no longer needs a sponsor, the guardian marks the plant as “done”.
- Action: Send a notification to the sponsor with an option to write a review.
- Tests: Notification sent after grower marks plant as “done” and sponsor review ends up on the grower’s profile.
