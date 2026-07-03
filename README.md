# GoliApp — ფეხბურთის მატჩების აპლიკაცია

**ავტორი:** [თქვენი სახელი და გვარი]  
**კურსი:** Android Development  
**დედლაინი:** 04.07.2026  
**GitHub:** [აქ ჩასვით რეპოზიტორიის ლინკი]

---

## აპლიკაციის აღწერა

**GoliApp** არის Android აპლიკაცია, რომელიც სპორტის მოყვარულებს საშუალებას აძლევს:

- იხილონ **დღევანდელი ფეხბურთის მატჩები** (API-დან პირდაპირ)
- გაფილტრონ მატჩები **ლიგის** მიხედვით (Premier League, La Liga და სხვა)
- ნახონ **ლიგის ცხრილი** (standings)
- **შეინახონ საყვარელი მატჩები** ლოკალურ ბაზაში და მოგვიანებით ნახონ Favourites გვერდზე
- გახსნან **მატჩის დეტალურ გვერდს** (გუნდები, ანგარიში, სტატუსი, თარიღი)

აპლიკაცია იყენებს [API-Football](https://www.api-football.com/) სერვისს მონაცემების მისაღებად.

---

## გამოცდის მოთხოვნების შესრულება

| მოთხოვნა | როგორ არის განხორციელებული |
|----------|---------------------------|
| **მენიუ** | ქვედა ნავიგაციის მენიუ (`BottomNavigationView`) — Home, Table, Favourites; Toolbar-ის overflow მენიუ — Refresh, About |
| **ლისთი** | `RecyclerView` — მატჩების სია, ლიგების ჩიპები, standings, favourites |
| **MVVM** | `HomeViewModel`, `StandingsViewModel`, `FavouritesViewModel`, `MatchDetailViewModel` + `LiveData` / `Flow` |
| **ბაზა** | **Retrofit** — REST API; **Room** — `favourite_matches` და `matches` ცხრილები |
| **findViewById აკრძალულია** | მთელ პროექტში გამოყენებულია **ViewBinding** (`FragmentHomeBinding`, `ActivityMainBinding` და ა.შ.) |
| **README** | ეს ფაილი |

---

## ახალი ფუნქციონალი (აქამდე არ გამოყენებული)

პროექტში გამოყენებულია **SwipeRefreshLayout (Pull-to-Refresh)** — მომხმარებელი თითის გაწევით ან Toolbar მენიუდან ახლდება მონაცემებს Home და Standings ეკრანებზე. ასევე გამოყენებულია **Navigation Component Safe Args** მატჩის ID-ის გადასაცემად დეტალურ გვერდზე.

---

## ტექნიკური სტეკი

| ტექნოლოგია | დანიშნულება |
|-----------|-------------|
| **Kotlin** | პროგრამირების ენა |
| **MVVM** | UI ლოგიკის გამოყოფა ViewModel-ებში |
| **ViewBinding** | XML ↔ Kotlin (findViewById-ის გარეშე) |
| **Hilt** | Dependency Injection |
| **Retrofit + OkHttp** | HTTP კლიენტი API-Football-თან |
| **Room** | SQLite ლოკალური ბაზა |
| **Navigation Component** | Fragment-ებს შორის ნავიგაცია |
| **Glide** | გუნდების/ლიგების ლოგოების ჩატვირთვა |
| **Coroutines + Flow** | ასინქრონული ოპერაციები |

---

## არქიტექტურა (MVVM)

```
UI Layer (Fragment + Adapter)
        ↕  observe LiveData / ViewBinding
ViewModel Layer (HomeViewModel, …)
        ↕
Repository Layer (FootballRepository, FavouritesRepository, StandingsRepository)
        ↕
Data Layer
  ├── Remote: FootballApiService (Retrofit)
  └── Local:  AppDatabase / Room (MatchDao, FavouriteDao)
```

---

## პროექტის სტრუქტურა

```
app/src/main/java/com/example/goliapp/
├── data/
│   ├── local/          # Room entities, DAOs, AppDatabase
│   └── remote/         # Retrofit API, DTOs
├── domain/model/       # Match, League, Standing + mappers
├── repository/         # FootballRepository, FavouritesRepository, …
├── ui/
│   ├── home/           # HomeFragment, HomeViewModel, adapters
│   ├── standings/      # StandingsFragment, StandingsViewModel
│   ├── favourites/     # FavouritesFragment, FavouritesViewModel
│   └── matches/        # MatchDetailFragment, MatchDetailViewModel
├── di/                 # Hilt modules (Network, Database)
└── utils/              # Constants, Resource, Extensions
```

---

## ეკრანები

1. **Home** — დღევანდელი მატჩები, ჰორიზონტალური ლიგის ფილტრი, pull-to-refresh
2. **Table** — Premier League standings (პოზიცია, გუნდი, ქულები)
3. **Favourites** — Room-ში შენახული მატჩები
4. **Match Detail** — სრული ინფორმაცია, ვარსკვლავით favourite-ად დამატება

---

## Room ბაზა

| ცხრილი | აღწერა |
|--------|--------|
| `matches` | API-დან მიღებული მატჩების ქეში (ოფლაინ რეჟიმისთვის) |
| `favourite_matches` | მომხმარებლის მიერ შენახული საყვარელი მატჩები |

---

## API გასაღები

`Constants.kt`-ში მითითებულია API-Football გასაღები. გაშვებამდე დარწმუნდით, რომ გასაღები აქტიურია.

---

## პროექტის გაშვება

1. Android Studio-ში გახსენით პროექტი
2. Gradle Sync
3. ემულატორი ან ფიზიკური მოწყობილობა (minSdk 24)
4. Run ▶

```bash
./gradlew assembleDebug
```

---

## Git-ზე ატვირთვა

```bash
git init
git add .
git commit -m "GoliApp final exam project"
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/GoliApp.git
git push -u origin main
```

**შენიშვნა:** .zip / .rar ფაილები არ მიიღება — მხოლოდ სრული Git რეპოზიტორია.

---

## ლიცენზია

სასწავლო პროექტი — Android Development საფაული