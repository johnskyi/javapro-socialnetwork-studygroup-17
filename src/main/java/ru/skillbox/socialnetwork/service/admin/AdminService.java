package ru.skillbox.socialnetwork.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skillbox.socialnetwork.data.dto.admin.PersonStatisticResponse;
import ru.skillbox.socialnetwork.data.dto.admin.StatisticRequest;
import ru.skillbox.socialnetwork.data.dto.admin.StatisticResponse;
import ru.skillbox.socialnetwork.data.entity.Person;
import ru.skillbox.socialnetwork.data.entity.Post;
import ru.skillbox.socialnetwork.data.entity.PostComment;
import ru.skillbox.socialnetwork.data.entity.PostLike;
import ru.skillbox.socialnetwork.data.repository.PersonRepo;
import ru.skillbox.socialnetwork.data.repository.PostCommentsRepository;
import ru.skillbox.socialnetwork.data.repository.PostLikesRepository;
import ru.skillbox.socialnetwork.data.repository.PostRepository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final PostRepository postRepository;
    private final PersonRepo personRepository;
    private final PostCommentsRepository postCommentsRepository;
    private final PostLikesRepository postLikesRepository;

    public StatisticResponse getPostStatistic(StatisticRequest request) {
        LocalDateTime from = LocalDateTime.parse(request.getDateFromGraph().substring(0, request.getDateFromGraph().indexOf(" ")));
        LocalDateTime to = LocalDateTime.parse(request.getDateToGraph().substring(0, request.getDateToGraph().indexOf(" ")));
        List<Post> totalPostsBetweenDates = postRepository.findAllByTimeBetweenDates(from, to);
        Map<Timestamp, Long> graphData = new TreeMap<>();
        Map<Integer, Double> postsByHour = new HashMap<>();

        List<Post> postsForDiagram = request.getDiagramPeriod().equals("allTime") ? postRepository.findAll() :
                postRepository.findAllByTimeBetweenDates(
                        LocalDateTime.parse(request.getDateFromDiagram().substring(0, request.getDateFromDiagram().indexOf(" "))),
                        LocalDateTime.parse(request.getDateToDiagram().substring(0, request.getDateToDiagram().indexOf(" ")))
                );

        for (int hour = 0; hour < 24; hour++){
            int finalHour = hour;
            long count = postsForDiagram.stream().filter(post -> post.getTime().getHour() == finalHour).count();
            postsByHour.put(hour, Math.floor(((double) count/postsForDiagram.size() * 100) * 1e2) / 1e2);
        }

        if(request.getGraphPeriod().equals("years")){
            while (from.getYear() <= to.getYear()){
                int finalYear = from.getYear();
                long count = totalPostsBetweenDates.stream().filter(post -> post.getTime().getYear() == finalYear).count();
                graphData.put(Timestamp.valueOf(from), count);
                from = from.plusYears(1);
            }
        } else if (request.getGraphPeriod().equals("months")){
            while (from.isBefore(to)){
                int finalMonth = from.getMonthValue();
                long count = totalPostsBetweenDates.stream().filter(post -> post.getTime().getMonthValue() == finalMonth).count();
                graphData.put(Timestamp.valueOf(from), count);
                from = from.plusMonths(1);
            }
        } else {
            while (from.isBefore(to)) {
                int finalDay = from.getDayOfMonth();
                long count = totalPostsBetweenDates.stream().filter(post -> post.getTime().getDayOfMonth() == finalDay).count();
                graphData.put(Timestamp.valueOf(from), count);
                from = from.plusDays(1);
            }
        }
        return statisticResponseBuild(graphData, postsByHour, totalPostsBetweenDates.size(), postRepository.count());
    }

    public PersonStatisticResponse getPersonStatistic(StatisticRequest request) {
        LocalDateTime from = LocalDateTime.parse(request.getDateFromGraph().substring(0, request.getDateFromGraph().indexOf(" ")));
        LocalDateTime to = LocalDateTime.parse(request.getDateToGraph().substring(0, request.getDateToGraph().indexOf(" ")));

        List<Person> totalPersonsBetweenDates = personRepository.findAllByRegTimeBetweenDates(from, to);
        Map<Timestamp, Long> graphData = new TreeMap<>();
        Map<String, Double> ageDistribution = ageDistributionMapBuild(totalPersonsBetweenDates);
        Map<String, Double> sexDistribution = sexDistributionMapBuild(totalPersonsBetweenDates);

        if(request.getGraphPeriod().equals("years")){
            while (from.getYear() <= to.getYear()){
                int finalYear = from.getYear();
                long count = totalPersonsBetweenDates.stream().filter(person -> person.getRegTime().getYear() == finalYear).count();
                graphData.put(Timestamp.valueOf(from), count);
                from = from.plusYears(1);
            }
        } else if(request.getGraphPeriod().equals("months")){
            while (from.isBefore(to)){
                int finalMonth = from.getMonthValue();
                long count = totalPersonsBetweenDates.stream().filter(person -> person.getRegTime().getMonthValue() == finalMonth).count();
                graphData.put(Timestamp.valueOf(from), count);
                from = from.plusMonths(1);
            }
        } else {
            while (from.isBefore(to)) {
                int finalDay = from.getDayOfMonth();
                long count = totalPersonsBetweenDates.stream().filter(person -> person.getRegTime().getDayOfMonth() == finalDay).count();
                graphData.put(Timestamp.valueOf(from), count);
                from = from.plusDays(1L);
            }
        }
        return personStatisticResponseBuild(graphData, ageDistribution, sexDistribution, totalPersonsBetweenDates.size());
    }

    public StatisticResponse getCommentStatistic(StatisticRequest request) {
        LocalDateTime from = LocalDateTime.parse(request.getDateFromGraph().substring(0, request.getDateFromGraph().indexOf(" ")));
        LocalDateTime to = LocalDateTime.parse(request.getDateToGraph().substring(0, request.getDateToGraph().indexOf(" ")));
        List<PostComment> totalPostCommentsBetweenDates = postCommentsRepository.findAllByTimeBetweenDates(from, to);
        Map<Timestamp, Long> graphData = new TreeMap<>();
        Map<Integer, Double> postCommentsByHour = new HashMap<>();

        List<PostComment> postCommentsForDiagram = request.getDiagramPeriod().equals("allTime") ? postCommentsRepository.findAll() :
                postCommentsRepository.findAllByTimeBetweenDates(
                        LocalDateTime.parse(request.getDateFromDiagram().substring(0, request.getDateFromDiagram().indexOf(" "))),
                        LocalDateTime.parse(request.getDateToDiagram().substring(0, request.getDateToDiagram().indexOf(" ")))
                );

        for (int hour = 0; hour < 24; hour++){
            int finalHour = hour;
            long count = postCommentsForDiagram.stream().filter(postComment -> postComment.getTime().getHour() == finalHour).count();
            postCommentsByHour.put(hour, Math.floor(((double) count/postCommentsForDiagram.size() * 100) * 1e2 / 1e2));
        }

        if(request.getGraphPeriod().equals("years")){
            while (from.getYear() <= to.getYear()){
                int finalYear = from.getYear();
                long count = totalPostCommentsBetweenDates.stream().filter(postComment -> postComment.getTime().getYear() == finalYear).count();
                graphData.put(Timestamp.valueOf(from), count);
                from = from.plusYears(1);
            }
        } else if(request.getGraphPeriod().equals("months")){
            while (from.isBefore(to)){
                int finalMonth = from.getMonthValue();
                long count = totalPostCommentsBetweenDates.stream().filter(postComment -> postComment.getTime().getMonthValue() == finalMonth).count();
                graphData.put(Timestamp.valueOf(from), count);
                from = from.plusMonths(1);
            }
        } else {
            while (from.isBefore(to)) {
                int finalDay = from.getDayOfMonth();
                long count = totalPostCommentsBetweenDates.stream().filter(postComment -> postComment.getTime().getDayOfMonth() == finalDay).count();
                graphData.put(Timestamp.valueOf(from), count);
                from = from.plusDays(1);
            }
        }
        return statisticResponseBuild(graphData, postCommentsByHour, totalPostCommentsBetweenDates.size(), postCommentsRepository.count());
    }

    public StatisticResponse getLikeStatistic(StatisticRequest request) {
        LocalDateTime from = LocalDateTime.parse(request.getDateFromGraph().substring(0, request.getDateFromGraph().indexOf(" ")));
        LocalDateTime to = LocalDateTime.parse(request.getDateToGraph().substring(0, request.getDateToGraph().indexOf(" ")));
        List<PostLike> totalLikeBetweenDates = postLikesRepository.findAllByTimeBetweenDates(from, to);
        Map<Timestamp, Long> graphData = new TreeMap<>();
        Map<Integer, Double> likesByHour = new HashMap<>();

        List<PostLike> likesForDiagram = request.getDiagramPeriod().equals("allTime") ? postLikesRepository.findAll() :
                postLikesRepository.findAllByTimeBetweenDates(
                        LocalDateTime.parse(request.getDateFromDiagram().substring(0, request.getDateFromDiagram().indexOf(" "))),
                        LocalDateTime.parse(request.getDateToDiagram().substring(0, request.getDateToDiagram().indexOf(" ")))
                );

        for (int hour = 0; hour < 24; hour++){
            int finalHour = hour;
            long count = likesForDiagram.stream().filter(like -> like.getTime().getHour() == finalHour).count();
            likesByHour.put(hour, Math.floor(((double) count/likesForDiagram.size() * 100) * 1e2 / 1e2));
        }

        if(request.getGraphPeriod().equals("years")){
            while (from.getYear() <= to.getYear()){
                int year = from.getYear();
                long count = totalLikeBetweenDates.stream().filter(like -> like.getTime().getYear() == year).count();
                graphData.put(Timestamp.valueOf(from), count);
                from = from.plusYears(1L);
            }
        } else if(request.getGraphPeriod().equals("months")){
            while (from.isBefore(to)){
                int month = from.getMonthValue();
                long count = totalLikeBetweenDates.stream().filter(like -> like.getTime().getMonthValue() == month).count();
                graphData.put(Timestamp.valueOf(from), count);
                from = from.plusMonths(1L);
            }
        } else {
            while (from.isBefore(to)){
                int day = from.getDayOfMonth();
                long count = totalLikeBetweenDates.stream().filter(like -> like.getTime().getDayOfMonth() == day).count();
                graphData.put(Timestamp.valueOf(from), count);
                from = from.plusDays(1L);
            }
        }
        return statisticResponseBuild(graphData, likesByHour, totalLikeBetweenDates.size(), postLikesRepository.count());
    }


    private Map<String, Double> sexDistributionMapBuild(List<Person> totalPersonsBetweenDates) {
        Map<String, Double> sexDistribution = new HashMap<>();
        sexDistribution.put("Женщины", Math.floor(((double) totalPersonsBetweenDates.stream().filter(p -> p.getGender().equals("Female")).count() / totalPersonsBetweenDates.size() * 100)  * 1e2 / 1e2));
        sexDistribution.put("Мужчины", Math.floor(((double) totalPersonsBetweenDates.stream().filter(p -> p.getGender().equals("Male")).count() / totalPersonsBetweenDates.size() * 100)  * 1e2 / 1e2));
        return sexDistribution;
    }



    private Map<String, Double> ageDistributionMapBuild(List<Person> totalPersons){
        Map<String, Double> ageDistribution = new HashMap<>();
        ageDistribution.put("0-18", 0.0);
        ageDistribution.put("19-23", 0.0);
        ageDistribution.put("24-30", 0.0);
        ageDistribution.put("31-45", 0.0);
        ageDistribution.put("46-60", 0.0);
        ageDistribution.put(">60", 0.0);
        totalPersons.forEach(person -> {
            int age = Period.between(person.getBirthTime().toLocalDate(), LocalDate.now()).getYears();
            if(age > 0 && age <= 18){
                ageDistribution.put("0-18", ageDistribution.get("0-18") + 1);
            }else if(age > 18 && age <= 23){
                ageDistribution.put("19-23", ageDistribution.get("19-23") + 1);
            }else if(age > 23 && age <= 30){
                ageDistribution.put("24-30", ageDistribution.get("24-30") + 1);
            }else if(age > 30 && age <= 45){
                ageDistribution.put("31-45", ageDistribution.get("31-45") + 1);
            }else if(age > 45 && age <= 60){
                ageDistribution.put("46-60", ageDistribution.get("46-60") + 1);
            } else {
                ageDistribution.put(">60", ageDistribution.get(">60") + 1);
            }
        });
        ageDistribution.forEach((s, aDouble) -> ageDistribution.put(s, Math.floor((aDouble/totalPersons.size() * 100) * 1e2) / 1e2));
        return ageDistribution;
    }

    private PersonStatisticResponse personStatisticResponseBuild(Map<Timestamp, Long> persons,
                                                                 Map<String, Double> ageDistribution,
                                                                 Map<String, Double> sexDistribution,
                                                                 int foundPersonCount){
        return PersonStatisticResponse.builder().error("string")
                .timestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .totalPersonCount(personRepository.count())
                .foundPersonCount(foundPersonCount)
                .personGraphData(persons)
                .ageDistribution(ageDistribution)
                .sexDistribution(sexDistribution)
                .build();
    }




    private StatisticResponse statisticResponseBuild(Map<Timestamp, Long> graphData,
                                                     Map<Integer, Double> dataByHour,
                                                     int foundDataCount,
                                                     long totalDataCount){
        return StatisticResponse.builder().error("string")
                .timestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .totalDataCount(totalDataCount)
                .foundDataCount(foundDataCount)
                .graphData(graphData)
                .dataByHour(dataByHour)
                .build();
    }
}
