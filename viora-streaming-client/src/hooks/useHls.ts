import {useEffect, useRef, useState} from "react";
import Hls from "hls.js";
import {getToken} from "../utils/apiUtils.ts";
import {SEGMENT_DURATION_SECONDS} from "../utils/historyHelpers.ts";

interface UseHlsOptions {
  src: string;
  videoRef: React.RefObject<HTMLVideoElement | null>;
  startFrom?: number;
}

interface UseHlsResult {
  isHlsLoading: boolean;
  hlsError: string | null;
  bufferedPercent: number;
}

export function useHls({
                         src,
                         videoRef,
                         startFrom = 0
                       }: UseHlsOptions): UseHlsResult {
  const hlsRef = useRef<Hls | null>(null);
  const [isHlsLoading, setIsHlsLoading] = useState(true);
  const [hlsError, setHlsError] = useState<string | null>(null);
  const [bufferedPercent, setBufferedPercent] = useState(0);

  useEffect(() => {
    const video = videoRef.current;
    if (!video || !src) return;

    setIsHlsLoading(true);
    setHlsError(null);
    setBufferedPercent(0);

    if (Hls.isSupported()) {
      const hls = new Hls({
        enableWorker: true,
        lowLatencyMode: false,
        xhrSetup: (xhr) => {
          const token = getToken();
          if (token) xhr.setRequestHeader("Authorization", `Bearer ${token}`);
        },
      });

      hlsRef.current = hls;
      hls.loadSource(src);
      hls.attachMedia(video);

      hls.on(Hls.Events.MANIFEST_PARSED, () => {
        video.currentTime = startFrom * SEGMENT_DURATION_SECONDS;
        setIsHlsLoading(false);
      });

      hls.on(Hls.Events.BUFFER_APPENDED, () => {
        if (video.buffered.length > 0 && video.duration) {
          setBufferedPercent(
              (video.buffered.end(video.buffered.length - 1) / video.duration) * 100
          );
        }
      });

      hls.on(Hls.Events.ERROR, (_event, data) => {
        if (data.fatal) {
          setHlsError(`HLS fatal error: ${data.type}`);
          console.error("HLS fatal error:", data);
          hls.destroy();
        }
      });

      return () => {
        hls.destroy();
        hlsRef.current = null;
      };
    } else if (video.canPlayType("application/vnd.apple.mpegurl")) {
      // Safari native HLS — token via query param
      const token = getToken();
      video.src = token ? `${src}?token=${encodeURIComponent(token)}` : src;
      const onMeta = () => setIsHlsLoading(false);
      video.addEventListener("loadedmetadata", onMeta);
      return () => video.removeEventListener("loadedmetadata", onMeta);
    } else {
      setHlsError("HLS is not supported in this browser.");
    }
  }, [src]);

  return {isHlsLoading, hlsError, bufferedPercent};
}