package net.sf.markov4jmeter.testplangenerator.transformation;

import m4jdsl.WorkloadModel;
import net.sf.markov4jmeter.testplangenerator.TestPlanElementFactory;
import net.sf.markov4jmeter.testplangenerator.transformation.filters.AbstractFilter;

import org.apache.jorphan.collections.ListedHashTree;

/**
 * Base class of all Test Plan transformer; such a transformer defines the core
 * structure of a Test Plan to be generated from a given M4J-DSL workload model.
 *
 * <p>This class is implemented as <i>builder</i> pattern:
 *
 * <p>Each subclass must implement the abstract <code>transform()</code> method
 * which returns a newly created Test Plan of a certain core structure. The
 * second, non-abstract <code>transform()</code> method invokes that method and
 * applies a sequence of filters to the newly created Test Plan additionally.
 *
 * @author   Eike Schulz (esc@informatik.uni-kiel.de)
 * @version  1.0
 */
public abstract class AbstractTestPlanTransformer {


    /* **************************  public methods  ************************** */


    /**
     * Builds a new Test Plan for the given M4J-DSL workload model and applies
     * a sequence of modification filters on it.
     *
     * @param workloadModel
     *     Workload model providing the information which is required for
     *     building a related Test Plan.
     * @param testPlanElementFactory
     *     Factory for creating Test Plan elements.
     * @param filters
     *     A sequence of filters to be applied on the newly created Test Plan;
     *     might be even <code>null</code>, if no filters shall be applied.
     *
     * @return
     *     A newly created Test Plan, possibly modified through the specified
     *     filters.
     */
    public ListedHashTree transform (
            final WorkloadModel workloadModel,
            final TestPlanElementFactory testPlanElementFactory,
            final AbstractFilter[] filters) {

        ListedHashTree testPlan =
                this.transform(workloadModel, testPlanElementFactory);

        if (filters != null) {

            for (final AbstractFilter filter : filters) {

                testPlan = filter.modifyTestPlan(
                        testPlan,
                        workloadModel,
                        testPlanElementFactory);
            }
        }

        return testPlan;
    }


    /* *************************  protected methods  ************************ */


    /**
     * Builds a new Test Plan for the given M4J-DSL workload model.
     *
     * @param workloadModel
     *     Workload model providing the information which is required for
     *     building a related Test Plan.
     * @param testPlanElementFactory
     *     Factory for creating Test Plan elements.
     *
     * @return
     *     A newly created Test Plan, structured as indicated by the regarding
     *     transformer.
     */
    protected abstract ListedHashTree transform (
            final WorkloadModel workloadModel,
            final TestPlanElementFactory testPlanElementFactory);
}