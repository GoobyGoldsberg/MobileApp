const functions = require("firebase-functions");
const stripe = require('stripe')('sk_test_51PMYd603iebvTiWJ1jrsf9R3Hi6nXt9VMljESM6OmRVGEFF60xUfTArbmNz5m4RRhjjUpxIrrTAKVJ8sxjHHvHYT00eAJ2u1Zb');


exports.helloWorld = functions.https.onRequest(async (request, response) => {

    
    // Use an existing Customer ID if this is a returning customer.
    const customer = await stripe.customers.create();
    const ephemeralKey = await stripe.ephemeralKeys.create(
        {customer: customer.id},
        {apiVersion: '2024-04-10'}
    );
    const paymentIntent = await stripe.paymentIntents.create({
        amount: 1099,
        currency: 'eur',
        customer: customer.id,
        // In the latest version of the API, specifying the `automatic_payment_methods` parameter
        // is optional because Stripe enables its functionality by default.
        automatic_payment_methods: {
        enabled: true,
        },
    });

    response.json({
        paymentIntent: paymentIntent.client_secret,
        ephemeralKey: ephemeralKey.secret,
        customer: customer.id,
        publishableKey: 'pk_test_51PMYd603iebvTiWJ6BKgGZnL4enCw8jSbs1Fx8fEOQDocLeRfJuHG49pTELGNFEORrqd0vypDLB6YPmdW93wfY3C00feg3YiwP'
    });
        
   
});
