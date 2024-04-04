	package Inifnite_Arithemetic;

	import java.util.Arrays;


	/**
	 * To Perform arithmetic operations on numbers represented as arrays
	 */
	public class Operations {
	    // Addition operation
	    /**
	     * This function is used to perform addition of two arrays
	     * @param arr1 First array of numbers
	     * @param arr2 Second array of numbers
	     * @return Addition of arr1 & arr2 represented as an array
	     */
	    public int[] addition(int[] arr1, int[] arr2) {
	        validateInputArray(arr1);
	        validateInputArray(arr2);

	        // Ensure arrays have the same length
	        int maxLength = Math.max(arr1.length, arr2.length);
	        int[] result = new int[maxLength];
	        int carry = 0;

	        for (int i = 0; i < maxLength; i++) {
	            int sum = carry;
	            if (i < arr1.length) {
	                sum += arr1[arr1.length - 1 - i];
	            }
	            if (i < arr2.length) {
	                sum += arr2[arr2.length - 1 - i];
	            }
	            result[maxLength - 1 - i] = sum % 10;
	            carry = sum / 10;
	        }

	        if (carry != 0) {
	            int[] newResult = new int[maxLength + 1];
	            newResult[0] = carry;
	            System.arraycopy(result, 0, newResult, 1, maxLength);
	            return newResult;
	        }

	        return result;
	    }

	    // Subtraction operation
	    /**
	     * This function is used to perform subtraction of two arrays
	     * @param arr1 First array of numbers
	     * @param arr2 Second array of numbers
	     * @return Subtraction of arr1 & arr2 represented as an array
	     */
	    public int[] subtraction(int[] arr1, int[] arr2) {
	        validateInputArray(arr1);
	        validateInputArray(arr2);

	        int[] result = new int[arr1.length];
	        int borrow = 0;

	        for (int i = 0; i < arr1.length; i++) {
	            int digit1 = i < arr1.length ? arr1[arr1.length - 1 - i] : 0;
	            int digit2 = i < arr2.length ? arr2[arr2.length - 1 - i] : 0;
	            int diff = digit1 - digit2 - borrow;

	            if (diff < 0) {
	                diff += 10;
	                borrow = 1;
	            } else {
	                borrow = 0;
	            }

	            result[arr1.length - 1 - i] = diff;
	        }

	        // Remove leading zeros
	        int index = 0;
	        while (index < result.length - 1 && result[index] == 0) {
	            index++;
	        }

	        return Arrays.copyOfRange(result, index, result.length);
	    }

	    // Multiplication operation
	    /**
	     * This function is used to perform multiplication of two arrays
	     * @param arr1 First array of numbers
	     * @param arr2 Second array of numbers
	     * @return Multiplication of arr1 & arr2 represented as an array
	     */
	    public int[] multiply(int[] arr1, int[] arr2) {
	        validateInputArray(arr1);
	        validateInputArray(arr2);

	        int[] result = new int[arr1.length + arr2.length];

	        for (int i = arr1.length - 1; i >= 0; i--) {
	            for (int j = arr2.length - 1; j >= 0; j--) {
	                int product = arr1[i] * arr2[j];
	                int sum = product + result[i + j + 1];
	                result[i + j] += sum / 10;
	                result[i + j + 1] = sum % 10;
	            }
	        }

	        // Remove leading zeros
	        int index = 0;
	        while (index < result.length - 1 && result[index] == 0) {
	            index++;
	        }

	        return Arrays.copyOfRange(result, index, result.length);
	    }

	    /** Validate input array */
	    private void validateInputArray(int[] arr) {
	        // Check if array is not empty
	        if (arr.length == 0) {
	            throw new IllegalArgumentException("Input array is empty.");
	        }

	        // Check if every element is a valid digit
	        for (int digit : arr) {
	            if (digit < 0 || digit > 9) {
	                throw new IllegalArgumentException("Array elements should be digits between 0 and 9.");
	            }
	        }
	    }
	    
	    
	    // Division operation
	    public int[] division(int[] dividend, int[] divisor) {
	        // Validate input arrays
	        validateInputArray(dividend);
	        validateInputArray(divisor);

	        // Normalize divisor to have the same length as dividend
	        int[] normalizedDivisor = new int[dividend.length];
	        System.arraycopy(divisor, 0, normalizedDivisor, 0, divisor.length);

	        // Perform division
	        int[] quotient = new int[dividend.length];
	        int[] remainder = dividend.clone(); // Initialize remainder as dividend

	        for (int i = 0; i < dividend.length; i++) {
	            int divisorDigit = normalizedDivisor[i];

	            if (divisorDigit == 0) // Avoid division by zero
	                break;

	            // Find the quotient digit
	            int digit = 0;
	            while (compareArrays(remainder, normalizedDivisor) >= 0) {
	                remainder = subtraction(remainder, normalizedDivisor);
	                digit++;
	            }

	            quotient[i] = digit;

	            // Shift the divisor for the next iteration
	            normalizedDivisor = shiftArray(normalizedDivisor, 1);
	        }

	        return quotient;
	    }
	    
	    // Compare two arrays (treat them as numbers)
	    private int compareArrays(int[] arr1, int[] arr2) {
	        if (arr1.length > arr2.length) return 1;
	        if (arr1.length < arr2.length) return -1;

	        for (int i = 0; i < arr1.length; i++) {
	            if (arr1[i] > arr2[i]) return 1;
	            if (arr1[i] < arr2[i]) return -1;
	        }

	        return 0;
	    }

	    // Shift array to the left (multiply by 10^shift)
	    private int[] shiftArray(int[] arr, int shift) {
	        int[] shiftedArray = new int[arr.length + shift];
	        System.arraycopy(arr, 0, shiftedArray, shift, arr.length);
	        return shiftedArray;
	    }

	    /** Main method for testing */
	    public static void main(String[] args) {
	        Operations arithmetic = new Operations();

	        // Testing addition
	        int[] arr1 = {1, 2, 3};
	        int[] arr2 = {1, 2, 3};
	        System.out.println("Addition: " + Arrays.toString(arithmetic.addition(arr1, arr2)));

	        // Testing subtraction
	        int[] arr3 = {1, 2, 3};
	        int[] arr4 = {1, 0, 0};
	        System.out.println("Subtraction: " + Arrays.toString(arithmetic.subtraction(arr3, arr4)));

	        // Testing multiplication
	        int[] arr5 = {1, 0};
	        int[] arr6 = {3, 0};
	        System.out.println("Multiplication: " + Arrays.toString(arithmetic.multiply(arr5, arr6)));
	        
	        // Testing division
	        int[] arr7 = {6, 0}; // 60
	        int[] arr8 = {2};    // 2
	        System.out.println("Division: " + Arrays.toString(arithmetic.division(arr7, arr8)));
	    }
	}
