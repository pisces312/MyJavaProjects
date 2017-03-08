package _2007.BuilderPattern.TooManyParameters;

/**
 * Representation of a United States address.
 * 
 * @author Dustin
 */
public final class Address {
  private final StreetAddress streetAddress;
  private final City city;
  private final State state;

  private Address(final StreetAddress newStreetAddress, final City newCity, final State newState) {
    this.streetAddress = newStreetAddress;
    this.city = newCity;
    this.state = newState;
  }

  public StreetAddress getStreetAddress() {
    return this.streetAddress;
  }

  public City getCity() {
    return this.city;
  }

  public State getState() {
    return this.state;
  }

  @Override
  public String toString() {
    return this.streetAddress + ", " + this.city + ", " + this.state;
  }

  public static class AddressBuilder {
    private StreetAddress nestedStreetAddress;
    private final City nestedCity;
    private final State nestedState;

    public AddressBuilder(final City newCity, final State newState) {
      this.nestedCity = newCity;
      this.nestedState = newState;
    }

    public AddressBuilder streetAddress(final StreetAddress newStreetAddress) {
      this.nestedStreetAddress = newStreetAddress;
      return this;
    }

    public Address createAddress() {
      return new Address(nestedStreetAddress, nestedCity, nestedState);
    }
  }
}
