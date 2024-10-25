import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class User {
    private String userId;
    private String username;
    private String password;
    private String email;
    private List<Auction> registeredAuctions;
    private List<Bid> bids;

    public User(String userId, String username, String password, String email) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.registeredAuctions = new ArrayList<>();
        this.bids = new ArrayList<>();
    }

    public void register() {
        // Logic for user registration
    }

    public void login() {
        // Logic for user login
    }

    // Getters and Setters
}

class Auction {
    private String auctionId;
    private String itemName;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double startPrice;
    private double currentPrice;
    private User highestBidder;
    private List<Bid> bids;
    private AuctionStatus auctionStatus;

    public Auction(String auctionId, String itemName, String description, LocalDateTime startTime, LocalDateTime endTime, double startPrice) {
        this.auctionId = auctionId;
        this.itemName = itemName;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startPrice = startPrice;
        this.currentPrice = startPrice;
        this.bids = new ArrayList<>();
        this.auctionStatus = AuctionStatus.ACTIVE;
    }

    public synchronized void placeBid(Bid bid) {
        if (auctionStatus != AuctionStatus.ACTIVE || LocalDateTime.now().isAfter(endTime)) {
            throw new IllegalStateException("Auction is not active or has ended");
        }

        if (bid.getBidAmount() > currentPrice) {
            currentPrice = bid.getBidAmount();
            highestBidder = bid.getBidder();
            bids.add(bid);
            notifyBidders();
        }
    }

    public void endAuction() {
        if (LocalDateTime.now().isAfter(endTime)) {
            auctionStatus = AuctionStatus.COMPLETED;
            // Additional logic to handle auction end
        }
    }

    private void notifyBidders() {
        // Logic to notify all bidders about the new highest bid
    }

    // Getters and Setters
}

class Bid {
    private String bidId;
    private double bidAmount;
    private LocalDateTime bidTime;
    private User bidder;
    private Auction auction;

    public Bid(String bidId, double bidAmount, User bidder, Auction auction) {
        this.bidId = bidId;
        this.bidAmount = bidAmount;
        this.bidTime = LocalDateTime.now();
        this.bidder = bidder;
        this.auction = auction;
    }

    public void placeBid() {
        auction.placeBid(this);
    }

    // Getters and Setters
}

enum AuctionStatus {
    ACTIVE,
    COMPLETED,
    CANCELLED
}

public class AuctionManagementSystem {
    private List<Auction> auctions;

    public AuctionManagementSystem() {
        this.auctions = new ArrayList<>();
    }

    public void createAuction(Auction auction) {
        auctions.add(auction);
    }

    public List<Auction> searchAuctions(String itemName, String category, double minPrice, double maxPrice) {
        // Logic to search auctions based on criteria
        return new ArrayList<>();
    }

    public void placeBid(Bid bid) {
        bid.placeBid();
    }

    public void endAuction(Auction auction) {
        auction.endAuction();
    }
}
