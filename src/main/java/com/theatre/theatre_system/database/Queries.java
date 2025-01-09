package com.theatre.theatre_system.database;

public record Queries() {
    public static final String ACTOR_QUERY = "SELECT e.last_name, e.first_name, e.middle_name, e.gender, e.birthday, a.*  FROM actors a JOIN employees e ON a.employee_id = e.employee_id";
    public static final String MUSICIAN_QUERY = "SELECT e.last_name, e.first_name, e.middle_name, e.gender, e.birthday, m.*  FROM musicians m JOIN employees e ON m.employee_id = e.employee_id";
    public static final String TICKET_QUERY = "SELECT * FROM tickets JOIN LATERAL (SELECT performances.name, performances.author, repertoires.id FROM repertoires JOIN performances ON repertoires.performance_id = performances.performance_id) subquery ON tickets.repertoire_id = subquery.id";
    public static final String PERFORMANCE_QUERY = "SELECT \n" +
            "    p.performance_id,\n" +
            "    p.name,\n" +
            "    p.genre,\n" +
            "    p.author,\n" +
            "    p.director_id, \n" +
            "    p.set_designer_id, \n" +
            "    p.conductor_id, \n" +
            "    CONCAT(d.last_name, ' ', d.first_name, ' ', d.middle_name) AS director_name,\n" +
            "    CONCAT(sd.last_name, ' ', sd.first_name, ' ', sd.middle_name) AS set_designer_name,\n" +
            "    CONCAT(c.last_name, ' ', c.first_name, ' ', c.middle_name) AS conductor_name\n" +
            "FROM \n" +
            "    performances p\n" +
            "LEFT JOIN \n" +
            "    employees d ON p.director_id = d.employee_id\n" +
            "LEFT JOIN \n" +
            "    employees sd ON p.set_designer_id = sd.employee_id\n" +
            "LEFT JOIN \n" +
            "    employees c ON p.conductor_id = c.employee_id\n";
    public static final String EMPLOYEE_QUERY = "SELECT * FROM employees";
    public static final String REPERTOIRE_QUERY = "SELECT p.name, p.genre, r.*  FROM repertoires r JOIN performances p ON r.performance_id = p.performance_id";
    public static final String ROLE_QUERY = "SELECT \n" +
            "    r.id AS role_id,\n" +
            "    r.role_name,\n" +
            "    p.performance_id,\n" +
            "    p.name AS performance_name,\n" +
            "    r.actor_id,\n" +
            "    r.understudy_id,\n" +
            "    CONCAT(a1.last_name, ' ', a1.first_name, ' ', a1.middle_name) AS actor_name,\n" +
            "    CONCAT(a2.last_name, ' ', a2.first_name, ' ', a2.middle_name) AS understudy_name\n" +
            "FROM \n" +
            "    public.roles r\n" +
            "LEFT JOIN \n" +
            "    public.performances p ON r.performance_id = p.performance_id\n" +
            "LEFT JOIN \n" +
            "    (SELECT employee_id, last_name, first_name, middle_name FROM employees) a1 ON r.actor_id = a1.employee_id\n" +
            "LEFT JOIN \n" +
            "    (SELECT employee_id, last_name, first_name, middle_name FROM employees) a2 ON r.understudy_id = a2.employee_id\n";
    public static final String TOUR_QUERY = "SELECT e.last_name, e.first_name, e.middle_name, e.gender, e.birthday, e.experience, t.*  FROM tours t JOIN employees e ON t.employee_id = e.employee_id";
}
