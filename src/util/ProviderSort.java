package util;

import clinic.Provider;

/**
 * Class for ProviderSort
 * @author Andrew Ho, Amit Deshpande
 */

public class ProviderSort {
    /**
     * Constructor for List
     * @param providers - providers to be placed in list
     * @return the List handled
     */
    public static List<Provider> sort(List<Provider> providers){
        List<Provider> tempProviders = new List<>();
        for (Provider p : providers){
            tempProviders.add(p);
        }

        int n = tempProviders.size();

        // One by one move boundary of unsorted subarray
        for (int i = 0; i < n - 1; i++) {
            // Find the minimum element in unsorted array
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (tempProviders.get(j).compareTo(tempProviders.get(minIndex)) < 0) {
                    minIndex = j;
                }
            }

            // Swap the found minimum element with the first element
            Provider temp = tempProviders.get(minIndex);
            tempProviders.set(minIndex, tempProviders.get(i));
            tempProviders.set(i, temp);
        }
        return tempProviders;
    }
}
