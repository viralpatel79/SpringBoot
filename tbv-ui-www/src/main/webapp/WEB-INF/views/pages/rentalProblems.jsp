<div class="container">

    <div class="col-md-6 offset-md-3">
      </br>
      <h3 class="text-center">Issues with your Rental?</h3>
      </br>
      <div class="col-md-10 offset-md-1">

      <p class="text-center text-muted">We're sorry to hear that! Let us know about your problem and we'll help you as quickly as we can.</p>
      <p class="text-center text-muted">What would you like to do?</p>
      </div>


      <ul class="nav nav-pills justify-content-center" role="tablist">
        <li class="nav-item">
          <a class="nav-link active" data-toggle="tab" href="#returnBooks" role="tab">Return Books</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" data-toggle="tab" href="#rentalProblems" role="tab">Rental Problems</a>
        </li>
      </ul>
      </br>

      <div class="tab-content">
        <div class="tab-pane active" id="returnBooks" role="tabpanel">
          <div class="col-md-10 offset-md-1">
            <p class="text-center text-muted">Fill out the form below, print your label, and ship your rental book back to us!</p>
          </div>

          <form>
            <div class="form-group">
              <label for="orderNumber">Order Number</label>
              <input type="text" class="form-control" id="orderNumber" aria-describedby="numberHelp" placeholder="XXXX-XXXX-XXXX">
              <small id="numberHelp" class="form-text text-muted">Find Order number in your Order confirmation email.</small>
            </div>
            <div class="form-group">
              <label for="userName">Name</label>
              <input type="text" class="form-control" id="userName" placeholder="John Doe">
            </div>
            <div class="form-group">
              <label for="phoneNumber">Phone number</label>
              <input type="text" class="form-control" id="phoneNumber" placeholder="">
            </div>
            <div class="form-group">
              <label for="addressStreet">Address</label>
              <input type="text" class="form-control" id="addressStreet" placeholder="Street address">
            </div>
            <div class="form-group">
              <input type="text" class="form-control" id="addressApt" placeholder="Apt. suite, etc. (optional)">
            </div>
            <div class="form-group">
              <input type="text" class="form-control" id="addressCity" placeholder="City">
            </div>
            <div class="form-group">
              <input type="text" class="form-control" id="addressState" placeholder="State">
            </div>
            <div class="form-group">
              <input type="text" class="form-control" id="addressZip" placeholder="ZIP">
            </div>
            <div class="form-check">
              <label class="form-check-label">
                <input type="checkbox" class="form-check-input">
                I'm part of the Rental Plan
              </label>
            </div>
            </br>
            <button type="submit" class="btn btn-primary btn-block">Get Return Label</button>
          </form>

        </div>


        <div class="tab-pane" id="rentalProblems" role="tabpane2">
          <div class="col-md-10 offset-md-1">
          <p class="text-center text-muted">Fill out the form below and we will help you as quickly as we can.</p>
        </div>

          <form>
            <div class="form-group">
              <label for="orderNumber">Order Number</label>
              <input type="text" class="form-control" id="orderNumber" aria-describedby="numberHelp" placeholder="XXXX-XXXX-XXXX">
              <small id="numberHelp" class="form-text text-muted">Find Order number in your Order confirmation email.</small>
            </div>

            <div class="form-group">
              <label for="userName">Name</label>
              <input type="text" class="form-control" id="userName" placeholder="John Doe">
            </div>

            <div class="form-group">
              <label for="phoneNumber">Phone number</label>
              <input type="text" class="form-control" id="phoneNumber" placeholder="">
            </div>

            <div class="form-group">
              <label for="addressStreet">Address</label>
              <input type="text" class="form-control" id="addressStreet" placeholder="Street address">
            </div>
            <div class="form-group">
              <input type="text" class="form-control" id="addressApt" placeholder="Apt. suite, etc. (optional)">
            </div>
            <div class="form-group">
              <input type="text" class="form-control" id="addressCity" placeholder="City">
            </div>
            <div class="form-group">
              <input type="text" class="form-control" id="addressState" placeholder="State">
            </div>
            <div class="form-group has-danger">
              <input type="text" class="form-control form-control-danger" id="addressZip" placeholder="ZIP">
                <div class="form-control-feedback">Oh! Looks like you forgot ZIP!</div>
            </div>
            <label>What's wrong?</label>
            <div class="form-check">
              <label class="form-check-label">
                <input type="checkbox" class="form-check-input">
                Wrong Book
              </label>
            </div>
            <div class="form-check">
              <label class="form-check-label">
                <input type="checkbox" class="form-check-input">
                Missing Book
              </label>
            </div>
            <div class="form-check">
              <label class="form-check-label">
                <input type="checkbox" class="form-check-input">
                Damaged Book
              </label>
            </div>
            <div class="form-check">
              <label class="form-check-label">
                <input type="checkbox" class="form-check-input">
                Late Return
              </label>
            </div>
            <div class="form-check">
              <label class="form-check-label">
                <input type="checkbox" class="form-check-input">
                Other (Please specify in comments)
              </label>
            </div>
            </br>
            <div class="form-group">
              <label for="userReturnNotes">Notes</label>
              <textarea class="form-control" id="userReturnNotes" rows="3"></textarea>
            </div>
            <button type="submit" class="btn btn-primary btn-block">Send</button>
          </form>

        </div>

      </div>
    </div>
  </div>
