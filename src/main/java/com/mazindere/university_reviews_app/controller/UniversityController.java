package com.mazindere.university_reviews_app.controller;

import com.mazindere.university_reviews_app.entity.Review;
import com.mazindere.university_reviews_app.model.University;
import com.mazindere.university_reviews_app.service.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UniversityController {

    public static final Map<String, University> universityData = new HashMap<>();
    private final ReviewService reviewService;

    public UniversityController(ReviewService reviewService){
        this.reviewService=reviewService;
    }

    // university data
    static {
        //mmu
        universityData.put("mmu", new University(
                "Multimedia University of Kenya",
                "mmu-hero.jpeg",
                "Multimedia University of Kenya (MMU) is a top institution in media, ICT," +
                        " engineering, and business studies, offering industry-focused programs and state-of-the-art " +
                        "facilities. Located in Nairobi along Magadi Road & near Ongata Rongai town, MMU provides hands-on training in broadcasting," +
                        " film production, software development and engineering fields like telecommunications " +
                        "and electrical engineering. With student led clubs, media stations, and strong industry links, students gain" +
                        " real-world experience through internships and career-driven education. If you're looking for a practical and " +
                        "dynamic learning environment, MMU is the place to be!",

                Arrays.asList("Faculty of Business and Economics",
                        "Faculty of Computing & Information Technology",
                        "Faculty of Engineering & Technology",
                        "Faculty of Media & Communication",
                        "Faculty of Science & Technology",
                        "Faculty of Social Sciences & Technology",
                        "National Institute for Optics & Lasers",
                        "MMU TVET Centre",
                        "Board of Post graduate Studies"),

                "https://www.mmu.ac.ke",
                "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3988.658655124362!2d36.76818919999999!3d-1.3814587!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x182f059a7c019c03%3A0x8092af97623fe89b!2sJQ99%2B98R%2C%20Nairobi!5e0!3m2!1sen!2ske!4v1740737475229!5m2!1sen!2ske"
        ));

        //ku
        universityData.put("ku", new University(
                "Kenyatta University",
                "ku-hero.jpg",
                "Kenyatta University is a leading public university in Kenya, located along Thika Rd in Nairobi " +
                        " and named after Kenya’s first president, it offers a wide range of undergraduate and postgraduate " +
                        "programs across disciplines like Business, Medicine, engineering, and Education. KU is known for its modern " +
                        "facilities, including a well-equipped library, a teaching and referral hospital, and an innovation hub. The " +
                        "university fosters a vibrant student life with various clubs, sports, and cultural events. Recognized for academic" +
                        " excellence and research, KU remains one of the top-ranked universities in Kenya and Africa.",

                Arrays.asList("School of Agriculture & Environmental Sciences",
                        "School of Business, Economics & Tourism",
                        "School of Education & Lifelong Learning",
                        "School of Engineering & Architecture",
                        "School of Health Sciences",
                        "School of Law, Arts & Social Sciences",
                        "School of Pure & Applied Sciences",
                        "Graduate School",
                        "Digital School of Virtual & Open Learning"),

                "https://www.ku.ac.ke",
                "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3988.969228824202!2d36.93409500959767!3d-1.182077698801638!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x182f3ff0f27b27f5%3A0xb0e3964cef8200a0!2sKenyatta%20University%2C%20Main%20Campus!5e0!3m2!1sen!2ske!4v1740806593380!5m2!1sen!2ske"
        ));

        //uon
        universityData.put("uon",new University(
                "University of Nairobi",
                "uon-hero.png",
                "The University of Nairobi is Kenya’s premier public university, established in 1970. It is renowned for its academic" +
                        " excellence, research, and innovation, offering a wide range of undergraduate and postgraduate programs across various" +
                        " disciplines, including medicine, engineering, business, law, and social sciences. With multiple campuses in Nairobi, UoN " +
                        "serves a large and diverse student population. It has produced notable alumni in politics, business, and academia. The " +
                        "university is also a hub for research and collaboration, contributing significantly to Kenya’s development.",

                Arrays.asList("Faculty of Law",
                        "Faculty of Education",
                        "Faculty of Agriculture",
                        "Faculty of The Built Environment & Design",
                        "Faculty of Health Sciences",
                        "Faculty of Health Sciences",
                        "Faculty of Science & Technology",
                        "Faculty of Engineering",
                        "Faculty of Veterinary Medicine",
                        "Faculty of Arts & Social Sciences",
                        "Faculty of Business & Management Sciences"),

                "https://www.uonbi.ac.ke/",
                "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d31910.569769189802!2d36.78006431083984!3d-1.2809709999999939!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x182f17a513a6231f%3A0x1ef6fdcc9f2d0cee!2sUniversity%20Of%20Nairobi%20-%20Main%20Campus!5e0!3m2!1sen!2ske!4v1742147923365!5m2!1sen!2ske"

        ));

        //tuk
        universityData.put("tuk", new University(
                "Technical University of Kenya",
                "tuk-hero.png",
                "The Technical University of Kenya (TUK) is a leading institution specializing in technical and applied sciences education." +
                        " Originally established as the Kenya Polytechnic in 1961, it gained university status in 2012. TUK offers programs" +
                        " in engineering, technology, architecture, business, and applied sciences, emphasizing hands-on training and " +
                        "innovation. Located in Nairobi, the university is known for producing skilled graduates who contribute to Kenya’s " +
                        "industrial and technological growth. With a strong focus on research and practical learning, TUK plays a vital role" +
                        " in bridging the gap between academia and industry.",

                Arrays.asList("Faculty of Engineering and the Built Environment (FEBE)",
                        "Faculty of Social Sciences and Technology (FSST) ",
                        "Faculty of Applied Sciences and Technology (FAST) "),

                "https://tukenya.ac.ke/",
                "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3988.8051498680165!2d36.822718773521935!3d-1.2912535356285455!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x182f10df5a23385d%3A0x15238033c2bcdc3b!2sThe%20Techniecal%20University%20Of%20Kenya!5e0!3m2!1sen!2ske!4v1742151956728!5m2!1sen!2ske"
        ));

        //chuka
        universityData.put("chuka", new University(
                "Chuka University",
                "chuka-hero.jpg",
                "Chuka University is a public institution located in Ndagani town, Tharaka Nithi County. It offers diverse programs in" +
                        " agriculture, business, education, engineering, and technology. Established in 2004 as a constituent college of " +
                        "Egerton University, it attained full university status in 2013. It is known for its scenic environment," +
                        " modern facilities, and commitment to academic excellence. With a strong focus on research, innovation, and practical" +
                        " learning, Chuka University plays a key role in advancing education and development in the region.",

                Arrays.asList("School of Nursing & Public Health",
                        "Faculty of Science and Technology",
                        "Faculty of Engineering",
                        "Faculty of Business Studies",
                        "Faculty of Education & Resources Development",
                        "Faculty of Agriculture",
                        "Faculty of Environment Studies & Resources Development",
                        "Faculty of Humanities & Social Sciences",
                        "School of Law"),

                "https://www.chuka.ac.ke/",
                "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3989.7562716372413!2d37.65490107355892!3d-0.3195420353306585!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x1827b9ef575b1ab7%3A0xfa2913c1ffafb42a!2sChuka%20University!5e0!3m2!1sen!2ske!4v1742972180066!5m2!1sen!2ske"
        ));


        //egerton
        universityData.put("egerton", new University(
                "Egerton University",
                "egerton-hero.jpg",
                "Egerton University, established in 1939 and chartered in 1987, is a leading public university in Kenya, renowned" +
                        " for its strong emphasis on agriculture, engineering, business, and education. Its main campus is in Njoro, Nakuru" +
                        " County, with a history of excellence in agricultural research and practical learning. The university is known for" +
                        " producing top graduates and contributing significantly to Kenya’s education and agricultural sectors. Guided by " +
                        "the motto \"Transforming Lives through Quality Education,\" it remains a key institution in higher learning" +
                        " and innovation.",

                Arrays.asList("Faculty of Agriculture",
                        "Faculty of Commerce",
                        "Faculty of Health Sciences",
                        "Faculty of Science",
                        "Faculty of Education & Community Studies",
                        "Faculty of Engineering & Technology",
                        "Faculty of Arts & Social Sciences",
                        "Institute of Gender, Women & Development Studies",
                        "Faculty of Environment & Resources Development",
                        "Faculty of Law",
                        "Faculty of Veterinary Medicine & Surgery"),

                "https://www.egerton.ac.ke/",
                "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2042743.819309178!2d33.626381193749985!3d-0.37245269999997554!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x182987e88ed4a835%3A0xd17ab41538bef8f6!2sEgerton%20University%20-%20Njoro!5e0!3m2!1sen!2ske!4v1743017189439!5m2!1sen!2ske"
        ));

        //jkuat
        universityData.put("jkuat", new University(
                "JKUAT",
                "jkuat-hero.png",
                "Jomo Kenyatta University of Agriculture and Technology (JKUAT) is a leading public university, specializing in" +
                        " technology, engineering, agriculture, and business studies. Established in 1981 as a middle-level college and " +
                        "later chartered as a university in 1994, it is known for its strong focus on innovation, research, and " +
                        "entrepreneurship. Its main campus is in Juja, Kiambu County, with several satellite campuses across Kenya. The " +
                        "university has played a key role in advancing science and technology in the country and is recognized for " +
                        "producing top professionals in various fields.",

                //todo-check this in their slow website
                Arrays.asList("School of Computing & Information Technology",
                        "School of Engineering",
                        "School of Business",
                        "School of Architecture and Building Sciences",
                        "School of Biomedical Sciences",
                        "School of Nursing",
                        "School of Agriculture and Environmental Sciences",
                        "School of Food and Nutrition Sciences",
                        "School of Law",
                        "School of Communication and Development Studies"),

                "https://www.jkuat.ac.ke/",
                "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3989.094529379069!2d37.00911437356301!3d-1.0913755354412156!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x182f46276c2d9021%3A0xbefd8e40071d3352!2sJomo%20Kenyatta%20University%20Of%20Agriculture%20And%20Technology!5e0!3m2!1sen!2ske!4v1743020736092!5m2!1sen!2ske"
        ));

        //maseno
        universityData.put("maseno", new University(
                "Maseno University",
                "maseno-hero.jpg",
                "Maseno University is known for its strong programs in IT, business, education, " +
                        "medicine, and environmental sciences. Established in 1991 and chartered in 2000, it is the only university in " +
                        "Kenya located along the Equator. The main campus is in Maseno, Kisumu County, with additional campuses in Kisumu " +
                        "City. Maseno is recognized for its blended learning approach, integrating e-learning and traditional teaching" +
                        " methods. With a commitment to academic excellence and innovation, the university continues to produce top" +
                        " graduates in various fields",

                Arrays.asList("School of Agriculture, Food Security & Environmental Sciences",
                        "School of Arts & Social Sciences",
                        "School of Business & Economics",
                        "School of Computing & Informatics",
                        "School of Education",
                        "School of Development & Strategic Studies",
                        "School of Planning & Architecture",
                        "School of Pharmacy",
                        "School of Medicine",
                        "School of Public Health & Community Development",
                        "School of Nursing",
                        "School of Physical & Biological Sciences",
                        "School of Mathematics, Statistics & Actuarial Sciences",
                        "Ecampus"),

                "https://www.maseno.ac.ke/",
                "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3989.818295453135!2d34.59480507316589!3d-0.006399906690910717!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x182aa97bdc7a35f1%3A0x20276056a9ac87c9!2sMaseno%20University!5e0!3m2!1sen!2ske!4v1743021662064!5m2!1sen!2ske"
        ));

        //meru
        universityData.put("meru", new University(
                "Meru University",
                "meru-hero.jpg",
                "Meru University of Science and Technology (MUST) is a public university in Kenya dedicated to advancing education in science," +
                        " technology, and innovation. Chartered in 2013, the university offers a wide range of programs in engineering, information" +
                        " technology, health sciences, business, agriculture, and education. Located in Meru County, MUST boasts modern facilities," +
                        " including well-equipped laboratories, a digital library, and an innovation hub that fosters research and entrepreneurship. " +
                        "The university provides a vibrant student life with numerous clubs, sports activities, and leadership opportunities.",

                Arrays.asList("School of Agriculture & Food Science",
                        "School of Business & Economics",
                        "School of Computing & Informatics",
                        "School of Education",
                        "School of Engineering & Architecture",
                        "School of Health Sciences",
                        "School of Pure & Applied Sciences",
                        "School of Nursing",
                        "School of Public Health & Community Development",
                        "School of Nursing"
                        ),

                "https://www.must.ac.ke/",
                "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d8419.991610350837!2d37.6977814935867!3d0.13550885642272037!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x17883c967d5c1517%3A0x94e1dbf4dfea47bd!2sMeru%20University%20of%20Science%20and%20Technology!5e0!3m2!1sen!2ske!4v1743490862907!5m2!1sen!2ske"
        ));

        //kisii
        universityData.put("kisii", new University(
                "Kisii University",
                "kisii-hero.png",
                "Kisii University College was founded in 1965 as a Primary Teachers Training College. The college continued up to 1983 when it was upgraded to a Secondary Teachers College to offer Diploma programmes. The Government of Kenya mandated Egerton University to take over the College as its campus in 1994."+
                        "In 1999, the Faculty of Commerce established Bachelor of Business and Management as its first degree program within the campus to run alongside Post Graduate Diploma in Education (PGDE), which was phased out in the year 2001. On 23rd August 2007, Kisii University College was established through a Government " +
                        "Legal Notice as a constituent College of Egerton University. On 6th February 2013, Former President Mwai Kibaki granted Kisii University Charter in accordance to the Universities Act 2012",

                Arrays.asList("School of Business & Economics",
                        "School of Agriculture & Natural Resources Management",
                        "School of Arts & Social Sciences",
                        "School of Education & Human Resource Development",
                        "School of Health Sciences",
                        "School of Information, Science & Technology",
                        "School of Pure & Applied Sciences",
                        "School of Law"
                ),

                "https://kisiiuniversity.ac.ke/",
                "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3989.5282839136353!2d34.78115862356073!3d-0.6908598852622834!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x182b3eaaf8db3f8b%3A0xae074315aedc0c34!2sKisii%20University!5e0!3m2!1sen!2ske!4v1743709793366!5m2!1sen!2ske"
        ));
    }

    @GetMapping("/universities/{uniName}")
    public String getUniversity(@PathVariable String uniName, Model model, HttpServletRequest request) {
        model.addAttribute("currentRequestUri", request.getRequestURI());
        University university = universityData.get(uniName);
        if (university == null) {
            return "error-page"; // Handle unknown universities
        }

        // Fetch reviews for this university
        List<Review> reviews = reviewService.getReviewsByUniversity(uniName);
        model.addAttribute("reviews", reviews); // Pass reviews to the Thymeleaf template
        model.addAttribute("university", university);
        return "university"; // Loads the base template dynamically
    }
}
