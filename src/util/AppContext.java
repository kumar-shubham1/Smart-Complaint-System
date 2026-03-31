package util;

import service.*;
import dao.ComplaintDAO;

public class AppContext {
    public static ComplaintService service = new ComplaintService();
    public static ComplaintDAO dao = new ComplaintDAO();

    // New DAA Services
    public static TopKService topKService = new TopKService();
    public static TrendAnalysisService trendService = new TrendAnalysisService();
    public static RabinKarpService rabinKarpService = new RabinKarpService();
    public static ComplaintClusterService clusterService = new ComplaintClusterService();
}