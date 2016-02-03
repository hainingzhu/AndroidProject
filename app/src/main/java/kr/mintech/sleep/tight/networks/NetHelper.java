package kr.mintech.sleep.tight.networks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import kr.mintech.sleep.tight.utils.EventLogger;

import bases.MTObject;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import Util.Logg;
import android.util.Log;

/**
 * A cached frontend to {@link UncachedNetHelper}. Most calls here are thin
 * wrappers to NetHelper.
 * @author mjskay
 *
 */
public class NetHelper extends MTObject {
	private static String TAG = "nethelper";
	
	/**
	 * id used for identifying this helper in logs
	 */
	private String id = this.toString(); 
	/**
	 * non-cached net helper we will use to make calls to the server
	 */
	private UncachedNetHelper netHelper;
	/**
	 * Cache used for the current call invoked on this NetHelper
	 */
	private Cache<List<Object>, Object> cache;
	/**
	 * Key corresponding to the arguments used to invoke this NetHelper
	 */
	private List<Object> cacheKey;

	public NetHelper(Object target, String method)
	{
		registerCallback(target, method);
		netHelper = new UncachedNetHelper(this, "processResponse");
	}
	
	//CALLBACKS USED WITH NetHelper
	/**
	 * Called to process a response from the server. This does not
	 * cache the response, it simply passes it on to the 
	 * target and method as specified in the constructor for
	 * this object.
	 * @param response
	 */
	public void processResponse(Object response) {
		// CLEANUP // Log.d(TAG, id + " <----------- INVOKED CALLBACK");
		invokeCallback(response);
	}
	/**
	 * Called to process a response from the server. This 
	 * caches the response only and does not pass it on to the 
	 * target and method as specified in the constructor for
	 * this object. Caches only if request was successful
	 *
	 * pre: cache and cacheKey are not null
	 * post: cache and cacheKey are null
	 *  
	 * @param response
	 */
	public void cacheResponse(Object response) {
		if (!(response instanceof NetworkResultUnit) || ((NetworkResultUnit)response).isSuccess) {
			//only cache successful responses
			// CLEANUP // Log.d(TAG, id + "  ----------- CACHED RESPONSE [" + cacheKey + "]");
			cache.put(cacheKey, response);
			cache = null;
			cacheKey = null;
		}
	}
	/**
	 * Called to process a response from the server. This 
	 * caches the response before passing it on to the 
	 * target and method as specified in the constructor for
	 * this object.
	 * @param response
	 */
	public void processAndCacheResponse(Object response) {
		cacheResponse(response);
		processResponse(response);
	}

	//CACHE HELPER METHODS
	/**
	 * Build a cache for the results to a call.
	 */
	private static Cache<List<Object>, Object> newCache() {
		return CacheBuilder.newBuilder()
			    .maximumSize(20)
			    .expireAfterAccess(30, TimeUnit.MINUTES)
			    .build();
	}
	/**
	 * Make a cached call over the network.
	 * - If the results of the called are in the cache, pass
	 *   them up to our callback without hitting the network
	 * - If the results of the call are not in the cache,
	 *   make a request over the network and cache the
	 *   result (asynchronously).
	 */
	private void cachedNetworkCall(Runnable netHelperCall) {
		Object cachedResponse = cache.getIfPresent(cacheKey);

		if (cachedResponse != null) {
			//the response was in the cache, so return it fast
			// CLEANUP // Log.d(TAG, id + "  ----------- GOT RESPONSE FROM CACHE [" + cacheKey + "]");
			processResponse(cachedResponse);
//			//then, update the cache from the network
//			netHelper.registerCallback(this, "cacheResponse");
//			netHelperCall.run();
		}
		else {
			//the response was not in the cache;
			//change the callback to cache the response before 
			//passing up the result from the server
			// CLEANUP // Log.d(TAG, id + "  ----------- NO RESPONSE IN CACHE [" + cacheKey + "]");
			netHelper.registerCallback(this, "processAndCacheResponse");
			netHelperCall.run();
		}
	}
	
	//WRAPPED METHODS
	

	//ACTIVITY TRACKS
	//caches for activity tracks
	/** cached responses to requestActivityTracks */
	private static Cache<List<Object>, Object> activityTracksCache = newCache();
	private void invalidateActivityTracksCaches() {
		//TODO: invalidate more narrowly?
		// CLEANUP // Log.d(TAG, id + "  ----------- INVALIDATING ACTIVITY TRACKS CACHE");
		activityTracksCache.invalidateAll();
		dailyBinInfoCache.invalidateAll();
	}
	//cacheable activity track queries
	/**
	 * Requesting ActivityTracks
	 */
	public void requestActivityTracks(final String baseTime, final int agoHours)
	{
		// CLEANUP // Log.d(TAG, id + " -----------> requestActivityTracks");

		cache = activityTracksCache;
		cacheKey = Arrays.asList((Object)baseTime, agoHours);
		cachedNetworkCall(new Runnable() { public void run() {
			netHelper.requestActivityTracks(baseTime, agoHours);
		}});
	}
	//uncacheable activity track queries
	/**
	 * Requesting to add ActivityTracks
	 */
	public void requestAddActivityTrack(int $activityId, String $actionStartedAt, String $actionEndedAt)
	{
		// CLEANUP // Log.d(TAG, id + " -----------> requestAddActivityTracks");
		invalidateActivityTracksCaches();
		netHelper.requestAddActivityTrack($activityId, $actionStartedAt, $actionEndedAt);
	}
	/**
	 * Requesting to edit ActivityTracks
	 */
	public void requestEditActivityTrack(int $trackId, int $activityId, String $actionStartedAt, String $actionEndedAt)
	{
		// CLEANUP // Log.d(TAG, id + " -----------> requestEditActivityTrack");
		invalidateActivityTracksCaches();
		netHelper.requestEditActivityTrack($trackId, $activityId, $actionStartedAt, $actionEndedAt);
	}
	/**
	 * Requesting to remove ActivityTracks
	 */
	public void requestRemoveActivityTrack(int $id)
	{
		// CLEANUP // Log.d(TAG, id + " -----------> requestRemoveActivityTrack");
		invalidateActivityTracksCaches();
		netHelper.requestRemoveActivityTrack($id);
	}
	
	
	//SLEEP TRACKS
	// caches for sleep queries
	private static Cache<List<Object>, Object> sleepTracksCache = newCache(); 
	private static Cache<List<Object>, Object> sleepDiaryExistCache = newCache();
	private static Cache<List<Object>, Object> dailySleepSummaryCache = newCache();
	private static Cache<List<Object>, Object> weeklySleepSummaryCache = newCache();
	private static Cache<List<Object>, Object> dailyBinInfoCache = newCache();
	private static Cache<List<Object>, Object> weeklyBinInfoCache = newCache();
	private void invalidateSleepCaches() {
		//TODO: invalidate more narrowly?
		// CLEANUP // Log.d(TAG, id + "  ----------- INVALIDATING SLEEP TRACKS CACHE");
		sleepTracksCache.invalidateAll();
		sleepDiaryExistCache.invalidateAll();
		dailySleepSummaryCache.invalidateAll();
		weeklySleepSummaryCache.invalidateAll();
		dailyBinInfoCache.invalidateAll();
		weeklyBinInfoCache.invalidateAll();
	}
	// cacheable sleep queries
	/**
	 * Requesting Sleep Track info
	 */
	public void requestSleepTracks(final String baseTime, final int agoHours)
	{
		// CLEANUP // Log.d(TAG, id + " -----------> requestSleepTracks");

		cache = sleepTracksCache;
		cacheKey = Arrays.asList((Object)baseTime, agoHours);
		cachedNetworkCall(new Runnable() {public void run() {
			netHelper.requestSleepTracks(baseTime, agoHours);
		}});
	}
	/**
	 * Requesting to check whether a Sleep Diary exists
	 */
	public void requestSleepDiaryExist(final String baseTime, final int agoHours)
	{
		// CLEANUP // Log.d(TAG, id + " -----------> requestSleepDiaryExist");

		cache = sleepDiaryExistCache;
		cacheKey = Arrays.asList((Object)baseTime, agoHours);
		cachedNetworkCall(new Runnable() {public void run() {
			netHelper.requestSleepDiaryExist(baseTime, agoHours);
		}});
	}
	//uncacheable sleep queries
	/**
	 * Requesting to add Sleep Info
	 */
	public void requestAddSleepInfo(String $inBedTime, int $sleepLetancy, String $wakeUpTime, String $outOfBedTime, String $diaryDate, int $sleepQ, int $awakeCount, int $totalWakeTime, 
			ArrayList<String> $sleepRitual, ArrayList<String> $sleepDisturbance)
	{
		// CLEANUP // Log.d(TAG, id + " -----------> requestAddSleepInfo");
		invalidateSleepCaches();
		netHelper.requestAddSleepInfo($inBedTime, $sleepLetancy, $wakeUpTime, $outOfBedTime, $diaryDate, $sleepQ, $awakeCount, $totalWakeTime, $sleepRitual, $sleepDisturbance);
	}
		
	//SLEEP SUMMARIES (based on Sleep Tracks data; cacheable)
	public void requestDailySleepSummary(final String endDate, final int numBin)
	{
		// CLEANUP // Log.d(TAG, id + " -----------> requestDailySleepSummary");
		cache = dailySleepSummaryCache;
		cacheKey = Arrays.asList((Object)endDate, numBin);
		cachedNetworkCall(new Runnable() {public void run() {
			netHelper.requestDailySleepSummary(endDate, numBin);
		}});
	}
	
	public void requestWeeklySleepSummary(final String endDate)
	{
		// CLEANUP // Log.d(TAG, id + " -----------> requestWeeklySleepSummary");
		cache = weeklySleepSummaryCache;
		cacheKey = Arrays.asList((Object)endDate);
		cachedNetworkCall(new Runnable() {public void run() {
			netHelper.requestWeeklySleepSummary(endDate);
		}});
	}
	
	public void requestDailyBinInfo(final String id)
	{
		// CLEANUP // Log.d(TAG, id + " -----------> requestDailyBinInfo");
		cache = dailyBinInfoCache;
		cacheKey = Arrays.asList((Object)id);
		cachedNetworkCall(new Runnable() {public void run() {
			netHelper.requestDailyBinInfo(id);
		}});
	}
	public void requestWeeklyBinInfo(final String id)
	{
		// CLEANUP // Log.d(TAG, id + " -----------> requestWeeklyBinInfo");
		cache = weeklyBinInfoCache;
		cacheKey = Arrays.asList((Object)id);
		cachedNetworkCall(new Runnable() {public void run() {
			netHelper.requestWeeklyBinInfo(id);
		}});
	}

	
	
	//ACTIONS
	// caches for action queries
	private static Cache<List<Object>, Object> actionsCache = newCache(); 
	private static Cache<List<Object>, Object> actionsShowAllCache = newCache();
	private static Cache<List<Object>, Object> actionsWithLimitCache = newCache();
	private void invalidateActionsCaches() {
		//TODO: invalidate more narrowly?
		// CLEANUP // Log.d(TAG, id + "  ----------- INVALIDATING ACTIONS CACHE");
		actionsCache.invalidateAll();
		actionsShowAllCache.invalidateAll();
		actionsWithLimitCache.invalidateAll();
	}
	// cacheable actions queries
	/**
	 * Requesting Actions (Caffeine, Meal ...)	
	 */
	public void requestActions()
	{
		// CLEANUP // Log.d(TAG, id + " -----------> requestActions");
		cache = actionsCache;
		cacheKey = new ArrayList<Object>();
		cachedNetworkCall(new Runnable() {public void run() {
			netHelper.requestActions();
		}});
	}
	public void requestActionsShowAll()
	{
		// CLEANUP // Log.d(TAG, id + " -----------> requestActionsShowAll");
		cache = actionsShowAllCache;
		cacheKey = new ArrayList<Object>();
		cachedNetworkCall(new Runnable() {public void run() {
			netHelper.requestActionsShowAll();
		}});
	}
	/**
	 * Requesting a certain number of actions [for widget]
	 */
	public void requestActionsWithLimit(final int limitCount)
	{
		// CLEANUP // Log.d(TAG, id + " -----------> requestActionsWithLimit");
		cache = actionsWithLimitCache;
		cacheKey = Arrays.asList((Object)limitCount);
		cachedNetworkCall(new Runnable() {public void run() {
			netHelper.requestActionsWithLimit(limitCount);
		}});
	}
	//uncacheable actions queries
	/**
	 * Requesting to remove ActionTracks
	 */
	public void requestRemoveAction(int $id)
	{
		// CLEANUP // Log.d(TAG, id + " -----------> requestRemoveAction");
		invalidateActionsCaches();
		netHelper.requestRemoveAction($id);
	}
	/**
	 * Requesting to add Actions
	 */
	public void requestAddAction(String $actionName)
	{
		// CLEANUP // Log.d(TAG, id + " -----------> requestAddAction");
		invalidateActionsCaches();
		netHelper.requestAddAction($actionName);
	}
	/**
	 * Requesting to hide Actions
	 * @type ACTIVITY_HIDE, ACTIVITY_SHOW, ACTIVITY_SORT
	 */
	public void requestManageAction(ArrayList<String> $hideArr, ArrayList<String> $showArr)
	{
		// CLEANUP // Log.d(TAG, id + " -----------> requestManageAction");
		invalidateActionsCaches();
		netHelper.requestManageAction($hideArr, $showArr);
	}
	/**
	 * Actions Sort
	 */
	public void requestManageSortAction(ArrayList<String> $sortArr)
	{
		// CLEANUP // Log.d(TAG, id + " -----------> requestManageSortAction");
		invalidateActionsCaches();
		netHelper.requestManageSortAction($sortArr);
	}
	

	//SLEEP DISTURBANCES
	//caches for sleep disturbances
	private static Cache<List<Object>, Object> sleepDisturbancesCache = newCache();
	private void invalidateSleepDisturbancesCaches() {
		//TODO: invalidate more narrowly?
		// CLEANUP // Log.d(TAG, id + "  ----------- INVALIDATING SLEEP DISTURBANCES CACHE");
		sleepDisturbancesCache.invalidateAll();
	}
	//cacheable sleep disturbance queries
	/**
	 * Requesting the sleep disturbance list
	 */
	public void requestSleepDisturb()
	{
		// CLEANUP // Log.d(TAG, id + " -----------> requestSleepDisturb");
		cache = sleepDisturbancesCache;
		cacheKey = new ArrayList<Object>();
		cachedNetworkCall(new Runnable() {public void run() {
			netHelper.requestSleepDisturb();
		}});
	}
	//uncacheable sleep disturbance queries
	/**
	 * Requesting to add a sleep disturbance
	 */
	public void requestAddSleepDisturb(String $name)
	{
		// CLEANUP // Log.d(TAG, id + " -----------> requestAddSleepDisturb");
		invalidateSleepDisturbancesCaches();
		netHelper.requestAddSleepDisturb($name);
	}
	/**
	 * Requesting to delete a sleep disturbance
	 */
	public void requestDeleteSleepDisturb(int $id)
	{
		// CLEANUP // Log.d(TAG, id + " -----------> requestDeleteSleepDisturb");
		invalidateSleepDisturbancesCaches();
		netHelper.requestDeleteSleepDisturb($id);
	}
	
	
	//ACTIVITIES BEFORE BED
	//caches for activities before bed
	private static Cache<List<Object>, Object> beforeBedActCache = newCache();
	private void invalidateBeforeBedActCaches() {
		//TODO: invalidate more narrowly?
		// CLEANUP // Log.d(TAG, id + "  ----------- INVALIDATING BEFORE BED ACTIVITIES CACHE");
		beforeBedActCache.invalidateAll();
	}
	//cacheable activities before bed queries
	/**
	 * Requesting the "Activities Before Bed" list
	 */
	public void requestBeforeBedAct()
	{
		// CLEANUP // Log.d(TAG, id + " -----------> requestBeforeBedAct");
		cache = beforeBedActCache;
		cacheKey = new ArrayList<Object>();
		cachedNetworkCall(new Runnable() {public void run() {
			netHelper.requestBeforeBedAct();
		}});
	}
	//uncacheable activities before bed queries
	/**
	 * Requesting to add an item to the "Activities Before Bed" list
	 */
	public void requestAddBeforeBedAct(String $name)
	{
		// CLEANUP // Log.d(TAG, id + " -----------> requestAddBeforeBedAct");
		invalidateBeforeBedActCaches();
		netHelper.requestAddBeforeBedAct($name);
	}
	/**
	 * Requesting to delete an item from the "Activities Before Bed" list
	 */
	public void requestDeleteBeforeBedAct(int $id)
	{
		// CLEANUP // Log.d(TAG, id + " -----------> requestDeleteBeforeBedAct");
		invalidateBeforeBedActCaches();
		netHelper.requestDeleteBeforeBedAct($id);
	}
	
	
	
	
	//COMPARISON
	//TODO: Could figure out caching for this. I don't think it's that important,
	//since it is typically called on a screen change. I haven't done it only because
	//I'm not sure exactly what data it depends on, so I'm unsure when its cache
	//would need to be invalidated.
	public void requestComparision()
	{
		// CLEANUP // Log.d(TAG, id + " -----------> requestComparision");
		netHelper.requestComparision();
	}
	
	
	
	//WRAPPED METHODS THAT (PROBABLY) SHOULDN'T NEED TO BE CHANGED OR CACHED
	/**
	 *  Cancel server request
	 */
	public void onRequestCancel() {
		// CLEANUP // Log.d(TAG, id + " -----------> onRequestCancel");
		netHelper.onRequestCancel();
	}
	
	/**
	 * Register Info
	 */
	public void requestIsRegister() {
		// CLEANUP // Log.d(TAG, id + " -----------> requestIsRegister");
		netHelper.requestIsRegister();
	}
	
	/**
	 * Put User Register Info
	 */
	public void requestEditRegisterInfo(int $targetUserId, String $userName, int $birthYear, String $gender, String $sleepCondition) 
	{
		// CLEANUP // Log.d(TAG, id + " -----------> requestEditRegisterInfo");
		netHelper.requestEditRegisterInfo($targetUserId, $userName, $birthYear, $gender, $sleepCondition);
	}
}