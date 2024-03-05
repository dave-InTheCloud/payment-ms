
const formatErrorMessage = (response, setError) =>  {
    if (response) { // Check if the response object exists
        const contentType = response.headers.get('Content-Type');
    
        if (contentType?.includes('application/json')) {
          // Response is JSON, try parsing the error
          response.json().then(body => {
            const formattedError = body.error.map(message => `- ${message}`).join('\n');
            setError(formattedError);
            console.error('Error fetching data:', formattedError);
          }).catch(error => {
            // Handle cases where JSON parsing fails
            console.error('Error parsing JSON response:', error);
            setError('An error occurred while processing the response.');
          });
        } else {
          // Response is not JSON, handle non-JSON response
          const errorMessage = `Unexpected response format: ${contentType}`;
          console.error('Error:', errorMessage);
          setError(errorMessage);
        }
      } else {
        // Handle other error scenarios where even the response object is unavailable
        setError('An unexpected network error occurred.');
        console.error('Error:', response); // Log the response (or error) for debugging
      }
}

export default formatErrorMessage;
